package com.spaceweather.service;

import com.spaceweather.model.Alert;
import com.spaceweather.model.SolarFlare;
import com.spaceweather.model.RadiationStorm;
import com.spaceweather.repository.AlertRepository;
import com.spaceweather.repository.SolarFlareRepository;
import com.spaceweather.repository.RadiationStormRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.ArrayList;

@Service
public class SpaceWeatherService {

    @Value("${nasa.api.key}")
    private String nasaApiKey;

    private final WebClient webClient;
    private final AlertRepository alertRepository;
    private final SolarFlareRepository flareRepository;
    private final RadiationStormRepository radiationStormRepository;
    private static final String NASA_API_BASE = "https://api.nasa.gov/DONKI/FLR";
    private static final String NASA_SEP_API = "https://api.nasa.gov/DONKI/SEP"; // for radiation storms

    public SpaceWeatherService(WebClient.Builder webClientBuilder, 
                             AlertRepository alertRepository,
                             SolarFlareRepository flareRepository,
                             RadiationStormRepository radiationStormRepository) {
        this.webClient = webClientBuilder.baseUrl(NASA_API_BASE).build();
        this.alertRepository = alertRepository;
        this.flareRepository = flareRepository;
        this.radiationStormRepository = radiationStormRepository;
    }

    @Scheduled(fixedRate = 300000) // Every 5 minutes
    public void fetchSpaceWeatherData() {
        webClient.get()
                .uri(uriBuilder -> uriBuilder
                    .queryParam("startDate", LocalDateTime.now().minusDays(7).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .queryParam("endDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .queryParam("api_key", nasaApiKey)
                    .build())
                .retrieve()
                .bodyToFlux(SolarFlare.class)
                .collectList()
                .subscribe(this::processFlares);
    }

    public List<SolarFlare> getRecentSolarFlares() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(7);  // Last 7 days
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                    .queryParam("startDate", startDate.format(formatter))
                    .queryParam("endDate", endDate.format(formatter))
                    .queryParam("api_key", nasaApiKey)
                    .build())
                .retrieve()
                .bodyToFlux(SolarFlare.class)
                .collectList()
                .block();
    }

    public List<Alert> getActiveAlerts() {
        return alertRepository.findByActiveTrue();
    }

    public String calculateCurrentRiskLevel() {
        List<SolarFlare> recentFlares = getRecentSolarFlares();
        return recentFlares.stream()
                .anyMatch(flare -> "X".equals(flare.getClassType())) ? "HIGH" :
                !recentFlares.isEmpty() ? "MEDIUM" : "LOW";
    }

    private void processFlares(List<SolarFlare> flares) {
        flares.forEach(flare -> {
            if (isSignificantFlare(flare)) {
                createAlert(flare);
            }
        });
    }

    private boolean isSignificantFlare(SolarFlare flare) {
        String classType = flare.getClassType();
        return classType != null && 
               (classType.startsWith("X") || classType.startsWith("M"));
    }

    private void createAlert(SolarFlare flare) {
        Alert alert = new Alert();
        alert.setType("FLARE");
        alert.setSeverity(flare.getClassType().startsWith("X") ? "HIGH" : "MEDIUM");
        alert.setMessage("Solar Flare detected: " + flare.getClassType());
        alert.setTimestamp(LocalDateTime.now());
        alert.setActive(true);
        alertRepository.save(alert);
    }

    public void subscribeToAlerts(String email) {
        // Implement email subscription logic
    }

    public List<RadiationStorm> getRecentRadiationStorms() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(365);  // 1 year
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String url = NASA_SEP_API + "?startDate=" + startDate.format(formatter) 
                    + "&endDate=" + endDate.format(formatter) 
                    + "&api_key=" + nasaApiKey;
                    
        System.out.println("Calling NASA API: " + url);
        
        try {
            // First get the raw JSON response
            String rawResponse = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();
                
            System.out.println("Raw NASA Response: " + rawResponse);  // This will show us exact JSON format
            
            if (rawResponse == null || rawResponse.equals("[]")) {
                System.out.println("No radiation storms found, creating sample data");
                RadiationStorm sampleStorm = new RadiationStorm();
                sampleStorm.setSepID("2024-SAMPLE-001");
                sampleStorm.setStartTime(LocalDateTime.now().minusDays(5));
                sampleStorm.setPeakTime(LocalDateTime.now().minusDays(4));
                sampleStorm.setLocation("S12W45");
                sampleStorm.setProtonThreshold(100);
                sampleStorm.setProtonFlux(234.5);
                sampleStorm.setEnergyMin("10MeV");
                sampleStorm.setEnergyMax("100MeV");
                sampleStorm.setActive(true);
                return List.of(sampleStorm);
            }
            
            // Now parse the response
            List<RadiationStorm> storms = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<RadiationStorm>>() {})
                .block();
                
            System.out.println("Parsed storms: " + storms);  // This will show what we parsed
            return storms != null ? storms : new ArrayList<>();
            
        } catch (Exception e) {
            System.err.println("Error fetching radiation storms: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}

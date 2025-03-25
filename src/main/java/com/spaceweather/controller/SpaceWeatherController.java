package com.spaceweather.controller;

import com.spaceweather.model.Alert;
import com.spaceweather.model.RadiationStorm;
import com.spaceweather.model.SolarFlare;
import com.spaceweather.service.SpaceWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/space-weather")
public class SpaceWeatherController {

    @Autowired
    private SpaceWeatherService weatherService;

    @GetMapping("/solar-flares")
    public List<SolarFlare> getRecentFlares() {
        return weatherService.getRecentSolarFlares();
    }

    @GetMapping("/test")
    public String test() {
        return "API is working with NASA API Key!";
    }

    @GetMapping("/alerts")
    public ResponseEntity<List<Alert>> getActiveAlerts() {
        return ResponseEntity.ok(weatherService.getActiveAlerts());
    }

    @GetMapping("/risk-level")
    public ResponseEntity<String> getCurrentRiskLevel() {
        return ResponseEntity.ok(weatherService.calculateCurrentRiskLevel());
    }

    @PostMapping("/alerts/subscribe")
    public ResponseEntity<String> subscribeToAlerts(@RequestParam String email) {
        weatherService.subscribeToAlerts(email);
        return ResponseEntity.ok("Subscribed successfully");
    }

    @GetMapping("/radiation-storms")
    public ResponseEntity<List<RadiationStorm>> getRadiationStorms() {
        return ResponseEntity.ok(weatherService.getRecentRadiationStorms());
    }
}

package com.spaceweather.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "radiation_storms")
public class RadiationStorm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JsonProperty("sepID")
    private String sepID;
    
    @JsonProperty("eventTime")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm'Z'")
    private LocalDateTime eventTime;
    
    @JsonProperty("peakTime")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm'Z'")
    private LocalDateTime peakTime;
    
    @JsonProperty("endTime")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm'Z'")
    private LocalDateTime endTime;
    
    @JsonProperty("location")
    private String location;
    
    @Column(name = "proton_threshold")
    @JsonProperty("protonThreshold")
    private Integer protonThreshold;
    
    @Column(name = "proton_flux")
    @JsonProperty("protonFlux")
    private Double protonFlux;
    
    @JsonProperty("energyMin")
    private String energyMin;
    
    @JsonProperty("energyMax")
    private String energyMax;
    
    @Column(length = 1000)
    @JsonProperty("link")
    private String link;
    
    @Column(nullable = false)
    private boolean active = false;
    
    // Declare startTime variable
    private java.time.LocalDateTime startTime;
    
    @Override
    public String toString() {
        return "RadiationStorm{" +
               "sepID='" + sepID + '\'' +
               ", eventTime=" + eventTime +
               ", link='" + link + '\'' +
               '}';
    }

    // Method to set start time
    public void setStartTime(java.time.LocalDateTime startTime) {
        this.startTime = startTime; // Assigning the parameter to the class variable
    }
}


//last me ye error check karna hai ai ko bolke taki radiation storm ka response aa ske

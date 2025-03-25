package com.spaceweather.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "solar_flares")
public class SolarFlare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String flrID;
    private String catalog;
    
    @Column(name = "class_type")
    private String classType;  // This is the flare class (M2.3, C9.6 etc)
    
    private LocalDateTime beginTime;
    private LocalDateTime peakTime;
    private LocalDateTime endTime;
    
    private String sourceLocation;
    private Integer activeRegionNum;
    private String note;
    
    @Column(length = 1000)
    private String link;

    // We'll ignore instruments and linkedEvents for now as they're complex objects
    // If you need them, we'll need to create separate entities
}

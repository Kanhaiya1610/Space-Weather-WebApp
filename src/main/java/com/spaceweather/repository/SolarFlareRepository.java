package com.spaceweather.repository;

import com.spaceweather.model.SolarFlare;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface SolarFlareRepository extends JpaRepository<SolarFlare, Long> {
    List<SolarFlare> findByBeginTimeAfter(LocalDateTime time);
} 
package com.spaceweather.repository;

import com.spaceweather.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findByActiveTrue();
}

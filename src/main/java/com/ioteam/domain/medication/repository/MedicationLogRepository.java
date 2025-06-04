package com.ioteam.domain.medication.repository;

import com.ioteam.domain.medication.entity.MedicationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MedicationLogRepository extends JpaRepository<MedicationLog, Long> {
    List<MedicationLog> findByUserId(Long userId);
}

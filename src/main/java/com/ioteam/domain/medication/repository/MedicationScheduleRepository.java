package com.ioteam.domain.medication.repository;

import com.ioteam.domain.medication.entity.MedicationSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicationScheduleRepository extends JpaRepository<MedicationSchedule, Long> {
    List<MedicationSchedule> findByUserId(Long userId);
}

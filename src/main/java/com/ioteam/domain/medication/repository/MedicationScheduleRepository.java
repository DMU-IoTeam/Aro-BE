package com.ioteam.domain.medication.repository;

import com.ioteam.domain.medication.entity.MedicationSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;

public interface MedicationScheduleRepository extends JpaRepository<MedicationSchedule, Long> {
    List<MedicationSchedule> findByUserId(Long userId);
    List<MedicationSchedule> findAllByTime(LocalTime time);

    @Query("SELECT DISTINCT ms FROM MedicationSchedule ms LEFT JOIN FETCH ms.items WHERE ms.user.id = :userId")
    List<MedicationSchedule> findByUserIdWithItems(@Param("userId") Long userId);
}
package com.ioteam.domain.health.repository;

import com.ioteam.domain.health.entity.HealthAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface HealthAnswerRepository extends JpaRepository<HealthAnswer, Long> {
    List<HealthAnswer> findAllBySeniorIdAndCheckDateBetween(Long seniorId, LocalDate startDate, LocalDate endDate);
}
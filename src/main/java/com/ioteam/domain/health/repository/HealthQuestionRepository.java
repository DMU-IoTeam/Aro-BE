package com.ioteam.domain.health.repository;

import com.ioteam.domain.health.entity.HealthQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HealthQuestionRepository extends JpaRepository<HealthQuestion, Long> {
    List<HealthQuestion> findAllByGuardianId(Long guardianId);
}
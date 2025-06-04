package com.ioteam.domain.medication.repository;

import com.ioteam.domain.medication.entity.MedicationItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationItemRepository extends JpaRepository<MedicationItem, Long> {
}

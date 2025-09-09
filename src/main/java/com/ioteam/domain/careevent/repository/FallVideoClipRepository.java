package com.ioteam.domain.careevent.repository;

import com.ioteam.domain.careevent.entity.FallVideoClip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FallVideoClipRepository extends JpaRepository<FallVideoClip, Long> {
    List<FallVideoClip> findByCareEventId(Long careEventId);
}

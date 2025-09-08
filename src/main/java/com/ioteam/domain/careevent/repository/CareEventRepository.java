package com.ioteam.domain.careevent.repository;

import com.ioteam.domain.careevent.entity.CareEvent;
import com.ioteam.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CareEventRepository extends JpaRepository<CareEvent, Long> {
    List<CareEvent> findBySeniorIn(List<User> seniors);
}

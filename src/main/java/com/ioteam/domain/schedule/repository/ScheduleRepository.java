package com.ioteam.domain.schedule.repository;

import com.ioteam.domain.schedule.entity.Schedule;
import com.ioteam.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllBySenior(User senior);
    List<Schedule> findAllByStartTimeBetween(LocalDateTime start, LocalDateTime end);
}
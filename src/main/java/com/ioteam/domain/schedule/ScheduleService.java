package com.ioteam.domain.schedule;

import com.ioteam.domain.schedule.dto.ScheduleRequest;
import com.ioteam.domain.schedule.dto.ScheduleResponse;
import com.ioteam.domain.schedule.entity.Schedule;
import com.ioteam.domain.schedule.repository.ScheduleRepository;
import com.ioteam.domain.user.entity.User;
import com.ioteam.domain.user.repository.UserRepository;
import com.ioteam.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long createSchedule(Long guardianId, ScheduleRequest request) {
        User senior = userRepository.findById(request.getSeniorId())
            .orElseThrow(() -> new EntityNotFoundException("피보호자를 찾을 수 없습니다."));

        if (senior.getGuardian() == null || !senior.getGuardian().getId().equals(guardianId)) {
            throw new AccessDeniedException("해당 피보호자의 일정을 등록할 권한이 없습니다.");
        }

        Schedule schedule = Schedule.builder()
            .senior(senior)
            .title(request.getTitle())
            .memo(request.getMemo())
            .startTime(request.getStartTime())
            .build();
        return scheduleRepository.save(schedule).getId();
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponse> getSchedules(Long guardianId, Long seniorId) {
        User senior = userRepository.findById(seniorId)
            .orElseThrow(() -> new EntityNotFoundException("피보호자를 찾을 수 없습니다."));

        if (senior.getGuardian() == null || !senior.getGuardian().getId().equals(guardianId)) {
            throw new AccessDeniedException("해당 피보호자의 일정을 조회할 권한이 없습니다.");
        }

        return scheduleRepository.findAllBySenior(senior).stream()
            .map(ScheduleResponse::new).collect(Collectors.toList());
    }

    @Transactional
    public void updateSchedule(Long guardianId, Long scheduleId, ScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
            .orElseThrow(() -> new EntityNotFoundException("일정을 찾을 수 없습니다."));

        if (schedule.getSenior().getGuardian() == null || !schedule.getSenior().getGuardian().getId().equals(guardianId)) {
            throw new AccessDeniedException("해당 일정을 수정할 권한이 없습니다.");
        }

        schedule.update(request.getTitle(), request.getMemo(), request.getStartTime());
    }

    @Transactional
    public void deleteSchedule(Long guardianId, Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
            .orElseThrow(() -> new EntityNotFoundException("일정을 찾을 수 없습니다."));

        if (schedule.getSenior().getGuardian() == null || !schedule.getSenior().getGuardian().getId().equals(guardianId)) {
            throw new AccessDeniedException("해당 일정을 삭제할 권한이 없습니다.");
        }

        scheduleRepository.deleteById(scheduleId);
    }
}
package com.ioteam.domain.schedule;

import com.ioteam.domain.schedule.dto.ScheduleRequest;
import com.ioteam.domain.schedule.dto.ScheduleResponse;
import com.ioteam.domain.user.entity.User;
import com.ioteam.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final UserRepository userRepository;

    private User getUserByAuth(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    @PostMapping
    public ResponseEntity<String> createSchedule(@RequestBody ScheduleRequest request, Authentication authentication) {
        User guardian = getUserByAuth(authentication);
        scheduleService.createSchedule(guardian.getId(), request);
        return ResponseEntity.ok("일정이 등록되었습니다.");
    }

    @GetMapping("/senior/{seniorId}")
    public ResponseEntity<List<ScheduleResponse>> getSchedules(@PathVariable Long seniorId, Authentication authentication) {
        User guardian = getUserByAuth(authentication);
        return ResponseEntity.ok(scheduleService.getSchedules(guardian.getId(), seniorId));
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<String> updateSchedule(@PathVariable Long scheduleId, @RequestBody ScheduleRequest request, Authentication authentication) {
        User guardian = getUserByAuth(authentication);
        scheduleService.updateSchedule(guardian.getId(), scheduleId, request);
        return ResponseEntity.ok("일정이 수정되었습니다.");
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<String> deleteSchedule(@PathVariable Long scheduleId, Authentication authentication) {
        User guardian = getUserByAuth(authentication);
        scheduleService.deleteSchedule(guardian.getId(), scheduleId);
        return ResponseEntity.ok("일정이 삭제되었습니다.");
    }
}
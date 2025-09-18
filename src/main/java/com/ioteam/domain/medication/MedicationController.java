package com.ioteam.domain.medication;

import com.ioteam.domain.medication.dto.*;
import com.ioteam.domain.user.entity.User;
import com.ioteam.domain.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medication")
@Tag(name = "Medication", description = "복약 일정 및 기록 관리 API")
@RequiredArgsConstructor
public class MedicationController {

    private final MedicationService medicationService;
    private final UserRepository userRepository;

    private User getUserByAuth(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    @PostMapping("/schedule")
    @Operation(summary = "복약 일정 등록", description = "보호자가 피보호자의 새로운 복약 일정을 등록합니다.")
    @ApiResponse(responseCode = "200", description = "일정 등록 성공")
    public ResponseEntity<MedicationScheduleResponse> create(
        @RequestBody MedicationScheduleRequest request,
        Authentication authentication) {
        User user = getUserByAuth(authentication);
        return ResponseEntity.ok(medicationService.createSchedule(request, user.getId()));
    }

    @GetMapping("/schedule/{seniorId}")
    @Operation(summary = "복약 일정 조회", description = "특정 피보호자의 복약 일정을 조회합니다.")
    public ResponseEntity<List<MedicationScheduleResponse>> getSchedules(@PathVariable Long seniorId) {
        return ResponseEntity.ok(medicationService.getSchedulesBySenior(seniorId));
    }

    @PutMapping("/schedule/{scheduleId}")
    @Operation(summary = "복약 일정 수정", description = "기존 복약 일정을 수정합니다.")
    public ResponseEntity<MedicationScheduleResponse> updateSchedule(
        @PathVariable Long scheduleId,
        @RequestBody MedicationScheduleRequest request,
        Authentication authentication) {
        User user = getUserByAuth(authentication);
        return ResponseEntity.ok(medicationService.updateSchedule(scheduleId, request, user.getId()));
    }

    @DeleteMapping("/schedule/{scheduleId}")
    @Operation(summary = "복약 일정 삭제", description = "기존 복약 일정을 삭제합니다.")
    public ResponseEntity<?> deleteSchedule(
        @PathVariable Long scheduleId,
        Authentication authentication) {
        User user = getUserByAuth(authentication);
        medicationService.deleteSchedule(scheduleId, user.getId());
        return ResponseEntity.ok("삭제 완료");
    }

    @PostMapping("/log")
    @Operation(summary = "복약 완료 기록", description = "피보호자가 복약을 완료했을 때 기록을 남깁니다.")
    public ResponseEntity<MedicationLogResponse> confirmMedication(
        @RequestBody MedicationLogRequest request,
        Authentication authentication) {
        User user = getUserByAuth(authentication);
        return ResponseEntity.ok(medicationService.confirmMedication(request, user.getId()));
    }

    @GetMapping("/log/{seniorId}")
    @Operation(summary = "복약 기록 조회", description = "특정 피보호자의 복약 완료 기록을 조회합니다.")
    public ResponseEntity<List<MedicationLogResponse>> getLogs(@PathVariable Long seniorId) {
        return ResponseEntity.ok(medicationService.getLogsBySenior(seniorId));
    }
}
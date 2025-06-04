package com.ioteam.domain.medication;

import com.ioteam.domain.medication.dto.*;
import com.ioteam.security.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medication")
@RequiredArgsConstructor
public class MedicationController {

    private final MedicationService medicationService;

    @PostMapping("/schedule")
    public ResponseEntity<MedicationScheduleResponse> create(
        @RequestBody MedicationScheduleRequest request,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(medicationService.createSchedule(request, userDetails.getId()));
    }

    @GetMapping("/schedule/{seniorId}")
    public ResponseEntity<List<MedicationScheduleResponse>> getSchedules(@PathVariable Long seniorId) {
        return ResponseEntity.ok(medicationService.getSchedulesBySenior(seniorId));
    }

    @PutMapping("/schedule/{scheduleId}")
    public ResponseEntity<MedicationScheduleResponse> updateSchedule(
        @PathVariable Long scheduleId,
        @RequestBody MedicationScheduleRequest request,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(medicationService.updateSchedule(scheduleId, request, userDetails.getId()));
    }

    @DeleteMapping("/schedule/{scheduleId}")
    public ResponseEntity<?> deleteSchedule(
        @PathVariable Long scheduleId,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        medicationService.deleteSchedule(scheduleId, userDetails.getId());
        return ResponseEntity.ok("삭제 완료");
    }

    @PostMapping("/log")
    public ResponseEntity<MedicationLogResponse> confirmMedication(
        @RequestBody MedicationLogRequest request,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(medicationService.confirmMedication(request, userDetails.getId()));
    }

    @GetMapping("/log/{seniorId}")
    public ResponseEntity<List<MedicationLogResponse>> getLogs(@PathVariable Long seniorId) {
        return ResponseEntity.ok(medicationService.getLogsBySenior(seniorId));
    }
}

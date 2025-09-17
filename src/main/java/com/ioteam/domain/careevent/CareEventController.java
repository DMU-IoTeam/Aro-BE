package com.ioteam.domain.careevent;

import com.ioteam.domain.careevent.dto.CareEventRequest;
import com.ioteam.domain.careevent.dto.CareEventResponse;
import com.ioteam.domain.user.entity.User;
import com.ioteam.security.service.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "CareEvent", description = "낙상 감지 이벤트 관리 API")
public class CareEventController {

    private final CareEventService careEventService;

    @PostMapping("/care-events")
    @Operation(summary = "낙상 이벤트 등록", description = "AI/하드웨어가 낙상을 감지했을 때 이벤트를 서버에 등록합니다.")
    @ApiResponse(responseCode = "200", description = "이벤트 등록 성공")
    public ResponseEntity<CareEventResponse> create(@RequestBody CareEventRequest request) {
        return ResponseEntity.ok(careEventService.createCareEvent(request));
    }

    @PreAuthorize("hasRole('GUARDIAN')")
    @GetMapping("/guardians/me/care-events")
    @Operation(summary = "내 피보호자 이벤트 조회", description = "보호자가 자신에게 속한 피보호자들의 낙상 이벤트 목록을 조회합니다.")
    public ResponseEntity<List<CareEventResponse>> list(@AuthenticationPrincipal CustomUserDetails userDetails) {
        User guardian = userDetails.getUser();
        return ResponseEntity.ok(careEventService.getEventsForGuardian(guardian));
    }

    @PreAuthorize("hasRole('GUARDIAN')")
    @GetMapping("/care-events/{id}")
    @Operation(summary = "이벤트 상세 조회", description = "낙상 이벤트의 상세 정보를 조회합니다.")
    public ResponseEntity<CareEventResponse> get(
        @PathVariable Long id,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(careEventService.getEvent(id, userDetails.getUser()));
    }

    @PreAuthorize("hasRole('GUARDIAN')")
    @PatchMapping("/care-events/{id}/ack")
    @Operation(summary = "이벤트 확인", description = "보호자가 특정 낙상 이벤트를 확인 처리합니다.")
    public ResponseEntity<CareEventResponse> ack(
        @PathVariable Long id,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(careEventService.ackEvent(id, userDetails.getUser()));
    }
}

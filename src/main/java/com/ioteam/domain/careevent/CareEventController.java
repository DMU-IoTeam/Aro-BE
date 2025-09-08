package com.ioteam.domain.careevent;

import com.ioteam.domain.careevent.dto.CareEventRequest;
import com.ioteam.domain.careevent.dto.CareEventResponse;
import com.ioteam.domain.user.entity.User;
import com.ioteam.security.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CareEventController {

    private final CareEventService careEventService;

    @PostMapping("/care-events")
    public ResponseEntity<CareEventResponse> create(@RequestBody CareEventRequest request) {
        return ResponseEntity.ok(careEventService.createCareEvent(request));
    }

    @PreAuthorize("hasRole('GUARDIAN')")
    @GetMapping("/guardians/me/care-events")
    public ResponseEntity<List<CareEventResponse>> list(@AuthenticationPrincipal CustomUserDetails userDetails) {
        User guardian = userDetails.getUser();
        return ResponseEntity.ok(careEventService.getEventsForGuardian(guardian));
    }

    @PreAuthorize("hasRole('GUARDIAN')")
    @GetMapping("/care-events/{id}")
    public ResponseEntity<CareEventResponse> get(@PathVariable Long id,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(careEventService.getEvent(id, userDetails.getUser()));
    }

    @PreAuthorize("hasRole('GUARDIAN')")
    @PatchMapping("/care-events/{id}/ack")
    public ResponseEntity<CareEventResponse> ack(@PathVariable Long id,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(careEventService.ackEvent(id, userDetails.getUser()));
    }
}

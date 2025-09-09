package com.ioteam.domain.careevent;

import com.ioteam.domain.careevent.dto.FallVideoClipRequest;
import com.ioteam.domain.careevent.dto.FallVideoClipResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/care-events/{eventId}/clips")
@RequiredArgsConstructor
public class FallVideoClipController {

    private final FallVideoClipService fallVideoClipService;

    @PostMapping
    public ResponseEntity<FallVideoClipResponse> addClip(@PathVariable Long eventId,
        @RequestBody FallVideoClipRequest request) {
        return ResponseEntity.ok(fallVideoClipService.addClip(eventId, request));
    }

    @PreAuthorize("hasRole('GUARDIAN')")
    @GetMapping
    public ResponseEntity<List<FallVideoClipResponse>> getClips(@PathVariable Long eventId) {
        return ResponseEntity.ok(fallVideoClipService.getClips(eventId));
    }
}

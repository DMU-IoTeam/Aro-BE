package com.ioteam.domain.careevent;

import com.ioteam.domain.careevent.dto.FallVideoClipRequest;
import com.ioteam.domain.careevent.dto.FallVideoClipResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Care Event", description = "돌봄 이벤트 (낙상, 침입) 관리 API")
@RestController
@RequestMapping("/api/care-events/{eventId}/clips")
@RequiredArgsConstructor
public class FallVideoClipController {

    private final FallVideoClipService fallVideoClipService;

    @Operation(summary = "이벤트 영상 추가", description = "특정 이벤트에 감지 영상을 추가합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "영상 추가 성공"),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 이벤트")
    })
    @PostMapping
    public ResponseEntity<FallVideoClipResponse> addClip(
        @Parameter(description = "영상을 추가할 이벤트 ID") @PathVariable Long eventId,
        @RequestBody FallVideoClipRequest request) {
        return ResponseEntity.ok(fallVideoClipService.addClip(eventId, request));
    }

    @Operation(summary = "이벤트 영상 조회", description = "특정 이벤트에 해당하는 영상 목록을 조회합니다. (보호자용)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "조회 성공"),
        @ApiResponse(responseCode = "403", description = "접근 권한 없음"),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 이벤트")
    })
    @PreAuthorize("hasRole('GUARDIAN')")
    @GetMapping
    public ResponseEntity<List<FallVideoClipResponse>> getClips(
        @Parameter(description = "영상을 조회할 이벤트 ID") @PathVariable Long eventId) {
        return ResponseEntity.ok(fallVideoClipService.getClips(eventId));
    }
}
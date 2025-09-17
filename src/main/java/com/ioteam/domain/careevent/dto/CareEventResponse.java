package com.ioteam.domain.careevent.dto;

import com.ioteam.domain.careevent.entity.CareEvent;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "돌봄 이벤트 응답 DTO")
public class CareEventResponse {

    @Schema(description = "이벤트 ID", example = "1")
    private Long id;

    @Schema(description = "이벤트 타입", example = "FALL")
    private String type;

    @Schema(description = "이벤트 상태 (DETECTED, NOTIFIED, ACKED, RESOLVED)", example = "DETECTED")
    private String status;

    @Schema(description = "이벤트 발생 시각", example = "2025-09-17T21:07:00")
    private LocalDateTime occurredAt;

    @Schema(description = "피보호자 User ID", example = "2")
    private Long seniorId;

    @Schema(description = "피보호자 이름", example = "김어르신")
    private String seniorName;

    public static CareEventResponse from(CareEvent event) {
        return CareEventResponse.builder()
            .id(event.getId())
            .type(event.getType().name())
            .status(event.getStatus().name())
            .occurredAt(event.getOccurredAt())
            .seniorId(event.getSenior().getId())
            .seniorName(event.getSenior().getName())
            .build();
    }
}
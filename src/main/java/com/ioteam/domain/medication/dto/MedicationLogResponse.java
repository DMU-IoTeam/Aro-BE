package com.ioteam.domain.medication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Schema(description = "복약 완료 기록 응답 DTO")
public class MedicationLogResponse {
    @Schema(description = "복약 기록 ID", example = "100")
    private Long logId;

    @Schema(description = "일정 ID", example = "10")
    private Long scheduleId;

    @Schema(description = "사용자 ID", example = "2")
    private Long userId;

    @Schema(description = "복약 확인 시간", example = "2025-09-17T08:30:00")
    private LocalDateTime confirmedAt;

    @Schema(description = "알림 전송 여부", example = "true")
    private boolean notified;

    @Schema(description = "기록 생성 시간", example = "2025-09-17T08:31:00")
    private LocalDateTime createdAt;
}


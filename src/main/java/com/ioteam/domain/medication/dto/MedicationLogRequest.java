package com.ioteam.domain.medication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Schema(description = "복약 완료 기록 요청 DTO")
public class MedicationLogRequest {
    @Schema(description = "복약 일정 ID", example = "10")
    private Long scheduleId;

    @Schema(description = "복약 확인 시간", example = "2025-09-17T08:30:00")
    private LocalDateTime confirmedAt;
}


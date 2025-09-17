package com.ioteam.domain.medication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Schema(description = "복약 일정 응답 DTO")
public class MedicationScheduleResponse {
    @Schema(description = "일정 ID", example = "5")
    private Long scheduleId;

    @Schema(description = "사용자 ID", example = "2")
    private Long userId;

    @Schema(description = "복약 시간", example = "08:30:00")
    private LocalTime time;

    @Schema(description = "복약 항목 리스트")
    private List<MedicationItemResponse> items;
}

package com.ioteam.domain.medication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import java.time.LocalTime;
import java.util.List;

@Getter
@Schema(description = "복약 일정 요청 DTO")
public class MedicationScheduleRequest {
    @Schema(description = "피보호자 ID", example = "2")
    private Long userId;

    @Schema(description = "복약 시간", example = "08:30:00")
    private LocalTime time;

    @Schema(description = "복약 항목 리스트")
    private List<MedicationItemRequest> items;
}

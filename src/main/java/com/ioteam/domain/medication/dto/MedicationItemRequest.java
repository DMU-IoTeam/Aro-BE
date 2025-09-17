package com.ioteam.domain.medication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "복약 항목 요청 DTO")
public class MedicationItemRequest {
    @Schema(description = "약 이름", example = "타이레놀")
    private String name;

    @Schema(description = "메모", example = "식후 30분 후 복용")
    private String memo;
}

package com.ioteam.domain.medication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "복약 항목 응답 DTO")
public class MedicationItemResponse {
    @Schema(description = "약 ID", example = "1")
    private Long id;

    @Schema(description = "약 이름", example = "타이레놀")
    private String name;

    @Schema(description = "메모", example = "식후 30분 후 복용")
    private String memo;
}


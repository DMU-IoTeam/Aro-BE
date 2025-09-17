package com.ioteam.domain.careevent.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CareEventRequest {

    @Schema(description = "피보호자 ID", example = "5")
    private Long seniorId;

    @Schema(description = "이벤트 타입", example = "FALL")
    private String type;
}

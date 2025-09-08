package com.ioteam.domain.careevent.dto;

import com.ioteam.domain.careevent.entity.CareEvent;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class CareEventResponse {
    private Long id;
    private String type;
    private String status;
    private LocalDateTime occurredAt;
    private Long seniorId;
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

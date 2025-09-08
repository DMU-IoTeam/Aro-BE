package com.ioteam.domain.careevent.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CareEventRequest {
    private Long seniorId;
    private String type;
}

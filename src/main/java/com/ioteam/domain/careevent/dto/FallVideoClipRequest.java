package com.ioteam.domain.careevent.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FallVideoClipRequest {
    private String videoUrl;
    private int duration;
}

package com.ioteam.domain.careevent.dto;

import com.ioteam.domain.careevent.entity.FallVideoClip;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class FallVideoClipResponse {
    private Long id;
    private String videoUrl;
    private int duration;
    private LocalDateTime recordedAt;

    public static FallVideoClipResponse from(FallVideoClip clip) {
        return FallVideoClipResponse.builder()
            .id(clip.getId())
            .videoUrl(clip.getVideoUrl())
            .duration(clip.getDuration())
            .recordedAt(clip.getRecordedAt())
            .build();
    }
}

package com.ioteam.domain.careevent.dto;

import com.ioteam.domain.careevent.entity.FallVideoClip;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "이벤트 영상 응답 DTO")
public class FallVideoClipResponse {

    @Schema(description = "영상 클립 ID", example = "1")
    private Long id;

    @Schema(description = "영상 URL", example = "https://aro-bucket.s3.ap-northeast-2.amazonaws.com/videos/clip_1.mp4")
    private String videoUrl;

    @Schema(description = "영상 길이", example = "20")
    private int duration;

    @Schema(description = "영상이 녹화된 시각", example = "2025-09-17T21:07:05")
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
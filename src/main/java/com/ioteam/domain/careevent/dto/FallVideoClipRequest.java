package com.ioteam.domain.careevent.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "이벤트 영상 추가 요청 DTO")
public class FallVideoClipRequest {

    @Schema(description = "S3 등에 업로드된 영상의 URL", example = "https://aro-bucket.s3.ap-northeast-2.amazonaws.com/videos/clip_1.mp4")
    private String videoUrl;

    @Schema(description = "영상의 길이", example = "20")
    private int duration;
}
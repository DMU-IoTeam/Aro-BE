package com.ioteam.domain.game.dto;

import com.ioteam.domain.game.entity.Photo;
import lombok.Getter;

@Getter
public class PhotoResponse {
    private Long photoId;
    private String imageUrl;
    private String caption;

    public PhotoResponse(Photo photo) {
        this.photoId = photo.getId();
        this.imageUrl = photo.getImageUrl();
        this.caption = photo.getCaption();
    }
}
package com.ioteam.domain.user;

import com.ioteam.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UserInfoResponse {
    @Schema(description = "사용자 ID", example = "1")
    private final Long id;

    @Schema(description = "사용자 이름", example = "김보호자")
    private final String name;

    @Schema(description = "이메일", example = "guardian@example.com")
    private final String email;

    @Schema(description = "역할 (GUARDIAN / SENIOR)", example = "GUARDIAN")
    private final String role;

    public UserInfoResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole().name();
    }
}

package com.ioteam.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "로그인 요청 DTO")
public class LoginRequest {

    @Schema(description = "사용자 이메일", example = "guardian@example.com")
    private String email;

    @Schema(description = "비밀번호", example = "123456")
    private String password;
}

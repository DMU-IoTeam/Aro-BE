package com.ioteam.auth;

import com.ioteam.auth.dto.AuthResponse;
import com.ioteam.auth.dto.SeniorLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login/senior")
    public ResponseEntity<AuthResponse> loginSenior(@RequestBody SeniorLoginRequest request) {
        return ResponseEntity.ok(userService.loginSenior(request));
    }
}
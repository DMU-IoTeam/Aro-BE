package com.ioteam.auth;

import com.ioteam.auth.dto.AuthResponse;
import com.ioteam.auth.dto.LoginRequest;
import com.ioteam.auth.dto.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest request) {
        userService.signup(request);
        return ResponseEntity.ok("회원가입 완료");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }
}

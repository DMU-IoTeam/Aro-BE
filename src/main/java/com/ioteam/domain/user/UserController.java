package com.ioteam.domain.user;

import com.ioteam.auth.UserService;
import com.ioteam.auth.dto.SeniorRegisterRequest;
import com.ioteam.auth.dto.SeniorRegisterResponse;
import com.ioteam.domain.user.entity.User;
import com.ioteam.domain.user.repository.UserRepository;
import com.ioteam.security.service.CustomUserDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('GUARDIAN')")
    public ResponseEntity<UserInfoResponse> getMyInfo(Authentication auth) {
        String email = auth.getName();
        User user = userRepository.findByEmail(email).orElseThrow();
        return ResponseEntity.ok(new UserInfoResponse(user));
    }

    @PostMapping("/register-senior")
    @PreAuthorize("hasRole('GUARDIAN')")
    public ResponseEntity<SeniorRegisterResponse> registerSenior(
        @RequestBody SeniorRegisterRequest request,
        @AuthenticationPrincipal CustomUserDetails userDetails) {

        SeniorRegisterResponse response = userService.registerSenior(request, userDetails.getId());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/senior/{id}")
    public ResponseEntity<SeniorRegisterResponse> updateSenior(
        @PathVariable Long id,
        @RequestBody SeniorRegisterRequest request,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(userService.updateSenior(id, request, userDetails.getId()));
    }

    @DeleteMapping("/senior/{id}")
    public ResponseEntity<?> deleteSenior(
        @PathVariable Long id,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.deleteSenior(id, userDetails.getId());
        return ResponseEntity.ok("삭제 완료");
    }

    @GetMapping("/seniors")
    public ResponseEntity<List<SeniorRegisterResponse>> getSeniors(
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(userService.findSeniorsByGuardian(userDetails.getId()));
    }

    @PatchMapping("/me/firebase-token")
    public ResponseEntity<?> updateFirebaseToken(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestBody String token) {
        userService.updateFirebaseToken(userDetails.getId(), token);
        return ResponseEntity.ok("Firebase 토큰이 업데이트되었습니다.");
    }
}

package com.ioteam.domain.user;

import com.ioteam.auth.UserService;
import com.ioteam.domain.user.dto.SeniorRegisterRequest;
import com.ioteam.domain.user.dto.SeniorRegisterResponse;
import com.ioteam.domain.user.entity.User;
import com.ioteam.domain.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User API", description = "보호자/피보호자 관리 API")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    private User getUserByAuth(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('GUARDIAN')")
    @Operation(summary = "내 정보 조회", description = "로그인한 보호자의 정보를 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "성공적으로 보호자 정보 반환"),
        @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    public ResponseEntity<UserInfoResponse> getMyInfo(Authentication authentication) {
        User user = getUserByAuth(authentication);
        return ResponseEntity.ok(new UserInfoResponse(user));
    }

    @PostMapping("/register-senior")
    @PreAuthorize("hasRole('GUARDIAN')")
    @Operation(summary = "피보호자 등록", description = "보호자가 새로운 피보호자를 등록합니다.")
    public ResponseEntity<SeniorRegisterResponse> registerSenior(
        @RequestBody @Parameter(description = "피보호자 등록 요청") SeniorRegisterRequest request,
        Authentication authentication) {
        User user = getUserByAuth(authentication);
        SeniorRegisterResponse response = userService.registerSenior(request, user.getId());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/senior/{id}")
    @Operation(summary = "피보호자 수정", description = "기존 피보호자 정보를 수정합니다.")
    public ResponseEntity<SeniorRegisterResponse> updateSenior(
        @PathVariable @Parameter(description = "수정할 피보호자 ID") Long id,
        @RequestBody SeniorRegisterRequest request,
        Authentication authentication) {
        User user = getUserByAuth(authentication);
        return ResponseEntity.ok(userService.updateSenior(id, request, user.getId()));
    }

    @DeleteMapping("/senior/{id}")
    @Operation(summary = "피보호자 삭제", description = "등록된 피보호자를 삭제합니다.")
    public ResponseEntity<?> deleteSenior(
        @PathVariable @Parameter(description = "삭제할 피보호자 ID") Long id,
        Authentication authentication) {
        User user = getUserByAuth(authentication);
        userService.deleteSenior(id, user.getId());
        return ResponseEntity.ok("삭제 완료");
    }

    @GetMapping("/seniors")
    @Operation(summary = "보호자 기준 피보호자 목록 조회", description = "보호자 계정에 등록된 모든 피보호자 목록을 조회합니다.")
    public ResponseEntity<List<SeniorRegisterResponse>> getSeniors(
        Authentication authentication) {
        User user = getUserByAuth(authentication);
        return ResponseEntity.ok(userService.findSeniorsByGuardian(user.getId()));
    }

    @PatchMapping("/me/firebase-token")
    @Operation(summary = "Firebase 토큰 등록/업데이트", description = "보호자 앱에서 받은 Firebase 토큰을 서버에 등록/갱신합니다.")
    public ResponseEntity<?> updateFirebaseToken(
        Authentication authentication,
        @RequestBody @Parameter(description = "Firebase 푸시 토큰") String token) {
        User user = getUserByAuth(authentication);
        userService.updateFirebaseToken(user.getId(), token);
        return ResponseEntity.ok("Firebase 토큰이 업데이트되었습니다.");
    }
}
package com.ioteam.domain.health;

import com.ioteam.domain.health.dto.*;
import com.ioteam.domain.user.entity.User;
import com.ioteam.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HealthController {
    private final HealthService healthService;
    private final UserRepository userRepository;

    private User getUserByAuth(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    @PostMapping("/health-questions")
    public ResponseEntity<String> createQuestion(@RequestBody QuestionRequest request, Authentication authentication) {
        User guardian = getUserByAuth(authentication);
        healthService.createQuestion(guardian.getId(), request);
        return ResponseEntity.ok("건강 확인 질문이 등록되었습니다.");
    }

    @PutMapping("/health-questions/{questionId}")
    public ResponseEntity<String> updateQuestion(@PathVariable Long questionId, @RequestBody QuestionRequest request, Authentication authentication) {
        User guardian = getUserByAuth(authentication);
        healthService.updateQuestion(guardian.getId(), questionId, request);
        return ResponseEntity.ok("질문이 수정되었습니다.");
    }

    @DeleteMapping("/health-questions/{questionId}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Long questionId, Authentication authentication) {
        User guardian = getUserByAuth(authentication);
        healthService.deleteQuestion(guardian.getId(), questionId);
        return ResponseEntity.ok("질문이 삭제되었습니다.");
    }

    @GetMapping("/seniors/{seniorId}/health-questions")
    public ResponseEntity<List<QuestionResponse>> getQuestions(@PathVariable Long seniorId) {
        return ResponseEntity.ok(healthService.getQuestions(seniorId));
    }

    @PostMapping("/health-answers")
    public ResponseEntity<String> createAnswers(@RequestBody List<AnswerRequest> requests, Authentication authentication) {
        User senior = getUserByAuth(authentication);
        healthService.createAnswers(senior.getId(), requests);
        return ResponseEntity.ok("오늘의 건강 상태가 기록되었습니다.");
    }

    @GetMapping("/seniors/{seniorId}/health-answers")
    public ResponseEntity<List<AnswerResponse>> getAnswers(
        @PathVariable Long seniorId,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
        Authentication authentication) {
        User guardian = getUserByAuth(authentication);
        return ResponseEntity.ok(healthService.getAnswers(guardian.getId(), seniorId, startDate, endDate));
    }
}
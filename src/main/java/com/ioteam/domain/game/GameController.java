package com.ioteam.domain.game;

import com.ioteam.domain.game.dto.AnswerRequest;
import com.ioteam.domain.game.dto.GameResultRequest;
import com.ioteam.domain.game.dto.PhotoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping("/photos")
    public ResponseEntity<String> uploadPhoto(
        @RequestPart("image") MultipartFile image,
        @RequestPart("caption") String caption,
        Authentication authentication) throws IOException {
        gameService.uploadPhoto(authentication, image, caption);
        return ResponseEntity.ok("사진이 성공적으로 업로드되었습니다.");
    }

    @GetMapping("/seniors/{seniorId}/photos")
    public ResponseEntity<List<PhotoResponse>> getPhotosForGame(@PathVariable Long seniorId) {
        List<PhotoResponse> photos = gameService.getPhotosForGame(seniorId);
        return ResponseEntity.ok(photos);
    }

    @PostMapping("/photos/{photoId}/check-answer")
    public ResponseEntity<Map<String, Boolean>> checkAnswer(
        @PathVariable Long photoId,
        @RequestBody AnswerRequest request) {
        boolean isCorrect = gameService.checkAnswer(photoId, request.getAnswer());
        return ResponseEntity.ok(Map.of("isCorrect", isCorrect));
    }

    @PostMapping("/game-results")
    public ResponseEntity<String> saveGameResult(@RequestBody GameResultRequest request) {
        gameService.saveGameResult(request);
        return ResponseEntity.ok("게임 결과가 저장되었습니다.");
    }
}
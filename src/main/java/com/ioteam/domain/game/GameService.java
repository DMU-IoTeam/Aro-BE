package com.ioteam.domain.game;

import com.ioteam.domain.game.dto.GameResultRequest;
import com.ioteam.domain.game.dto.PhotoResponse;
import com.ioteam.domain.game.entity.GameResult;
import com.ioteam.domain.game.entity.Photo;
import com.ioteam.domain.game.repository.GameResultRepository;
import com.ioteam.domain.game.repository.PhotoRepository;
import com.ioteam.domain.user.entity.User;
import com.ioteam.domain.user.repository.UserRepository;
import com.ioteam.global.storage.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService {
    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;
    private final GameResultRepository gameResultRepository;
    private final FileUploadService fileUploadService;

    private User getUserByAuth(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    @Transactional
    public void uploadPhoto(Authentication authentication, MultipartFile image, String caption) throws IOException {
        User guardian = getUserByAuth(authentication);

        String imageUrl = fileUploadService.upload(image, "photos");

        if (imageUrl == null) {
            throw new IOException("파일 업로드에 실패했습니다.");
        }

        Photo photo = Photo.builder()
            .guardian(guardian)
            .imageUrl(imageUrl)
            .caption(caption)
            .build();

        photoRepository.save(photo);
    }

    @Transactional(readOnly = true)
    public List<PhotoResponse> getPhotosForGame(Long seniorId) {
        User senior = userRepository.findById(seniorId)
            .orElseThrow(() -> new IllegalArgumentException("피보호자를 찾을 수 없습니다."));

        User guardian = senior.getGuardian();
        if (guardian == null) {
            return Collections.emptyList();
        }

        List<Photo> photos = photoRepository.findAllByGuardian(guardian);
        Collections.shuffle(photos);

        return photos.stream()
            .map(PhotoResponse::new)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public boolean checkAnswer(Long photoId, String userAnswer) {
        Photo photo = photoRepository.findById(photoId)
            .orElseThrow(() -> new IllegalArgumentException("사진을 찾을 수 없습니다."));

        if (userAnswer == null || userAnswer.trim().isEmpty()) {
            return false;
        }

        String[] correctAnswers = photo.getCaption().split(",");
        String cleanedUserAnswer = userAnswer.replaceAll("\\s+", "");

        return Arrays.stream(correctAnswers)
            .map(answer -> answer.trim().replaceAll("\\s+", ""))
            .anyMatch(answer -> answer.equalsIgnoreCase(cleanedUserAnswer));
    }

    @Transactional
    public void saveGameResult(GameResultRequest request) {
        User senior = userRepository.findById(request.getSeniorId())
            .orElseThrow(() -> new IllegalArgumentException("피보호자를 찾을 수 없습니다."));

        GameResult result = GameResult.builder()
            .senior(senior)
            .gameType(request.getGameType())
            .score(request.getScore())
            .totalQuestions(request.getTotalQuestions())
            .build();

        gameResultRepository.save(result);
    }
}
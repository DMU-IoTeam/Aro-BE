package com.ioteam.domain.careevent;

import com.ioteam.domain.careevent.dto.FallVideoClipResponse;
import com.ioteam.domain.careevent.entity.CareEvent;
import com.ioteam.domain.careevent.entity.FallVideoClip;
import com.ioteam.domain.careevent.repository.CareEventRepository;
import com.ioteam.domain.careevent.repository.FallVideoClipRepository;
import com.ioteam.domain.user.entity.User;
import com.ioteam.global.exception.EntityNotFoundException;
import com.ioteam.global.notification.NotificationService;
import com.ioteam.global.storage.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FallVideoClipService {

    private final FallVideoClipRepository fallVideoClipRepository;
    private final CareEventRepository careEventRepository;
    private final FileUploadService fileUploadService;
    private final NotificationService notificationService;

    public FallVideoClipResponse addClip(Long eventId, MultipartFile videoFile, int duration) throws IOException {
        CareEvent event = careEventRepository.findById(eventId)
            .orElseThrow(() -> new EntityNotFoundException("해당 이벤트를 찾을 수 없습니다."));

        String videoUrl = fileUploadService.upload(videoFile, "videos");

        FallVideoClip clip = FallVideoClip.builder()
            .careEvent(event)
            .videoUrl(videoUrl)
            .duration(duration)
            .build();

        fallVideoClipRepository.save(clip);

        User senior = event.getSenior();
        User guardian = senior.getGuardian();
        if (guardian != null && guardian.getFirebaseToken() != null) {
            notificationService.sendPushNotification(
                guardian.getFirebaseToken(),
                "낙상 감지",
                senior.getName() + " 님에게 낙상이 감지되었습니다.",
                Map.of("careEventId", event.getId().toString())
            );
        }

        return FallVideoClipResponse.from(clip);
    }

    public List<FallVideoClipResponse> getClips(Long eventId) {
        return fallVideoClipRepository.findByCareEventId(eventId)
            .stream().map(FallVideoClipResponse::from)
            .collect(Collectors.toList());
    }
}
package com.ioteam.domain.careevent;

import com.ioteam.domain.careevent.dto.CareEventRequest;
import com.ioteam.domain.careevent.dto.CareEventResponse;
import com.ioteam.domain.careevent.entity.CareEvent;
import com.ioteam.domain.careevent.repository.CareEventRepository;
import com.ioteam.domain.user.entity.User;
import com.ioteam.domain.user.repository.UserRepository;
import com.ioteam.global.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CareEventService {

    private final CareEventRepository careEventRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public CareEventResponse createCareEvent(CareEventRequest request) {
        User senior = userRepository.findById(request.getSeniorId())
            .orElseThrow(() -> new IllegalArgumentException("해당 피보호자를 찾을 수 없습니다."));

        CareEvent event = CareEvent.builder()
            .senior(senior)
            .type(CareEvent.EventType.valueOf(request.getType()))
            .status(CareEvent.Status.DETECTED)
            .build();

        careEventRepository.save(event);

        User guardian = senior.getGuardian();
        if (guardian != null && guardian.getFirebaseToken() != null) {
            notificationService.sendPushNotification(
                guardian.getFirebaseToken(),
                "낙상 감지",
                senior.getName() + " 님에게 낙상이 감지되었습니다."
            );
        }

        return CareEventResponse.from(event);
    }

    public List<CareEventResponse> getEventsForGuardian(User guardian) {
        List<User> seniors = userRepository.findByGuardianIdAndRole(guardian.getId(), User.Role.SENIOR);
        return careEventRepository.findBySeniorIn(seniors)
            .stream().map(CareEventResponse::from)
            .collect(Collectors.toList());
    }

    public CareEventResponse getEvent(Long id, User guardian) {
        CareEvent event = careEventRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("이벤트를 찾을 수 없습니다."));
        if (!event.getSenior().getGuardian().getId().equals(guardian.getId())) {
            throw new SecurityException("이 이벤트에 접근할 권한이 없습니다.");
        }
        return CareEventResponse.from(event);
    }

    public CareEventResponse ackEvent(Long id, User guardian) {
        CareEvent event = careEventRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("이벤트를 찾을 수 없습니다."));
        if (!event.getSenior().getGuardian().getId().equals(guardian.getId())) {
            throw new SecurityException("이 이벤트를 확인할 권한이 없습니다.");
        }
        event.ack();
        careEventRepository.save(event);
        return CareEventResponse.from(event);
    }
}

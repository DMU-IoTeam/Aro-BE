package com.ioteam.domain.careevent;

import com.ioteam.domain.careevent.dto.FallVideoClipRequest;
import com.ioteam.domain.careevent.dto.FallVideoClipResponse;
import com.ioteam.domain.careevent.entity.CareEvent;
import com.ioteam.domain.careevent.entity.FallVideoClip;
import com.ioteam.domain.careevent.repository.CareEventRepository;
import com.ioteam.domain.careevent.repository.FallVideoClipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FallVideoClipService {

    private final FallVideoClipRepository fallVideoClipRepository;
    private final CareEventRepository careEventRepository;

    public FallVideoClipResponse addClip(Long eventId, FallVideoClipRequest request) {
        CareEvent event = careEventRepository.findById(eventId)
            .orElseThrow(() -> new IllegalArgumentException("해당 이벤트를 찾을 수 없습니다."));

        FallVideoClip clip = FallVideoClip.builder()
            .careEvent(event)
            .videoUrl(request.getVideoUrl())
            .duration(request.getDuration())
            .build();

        fallVideoClipRepository.save(clip);
        return FallVideoClipResponse.from(clip);
    }

    public List<FallVideoClipResponse> getClips(Long eventId) {
        return fallVideoClipRepository.findByCareEventId(eventId)
            .stream().map(FallVideoClipResponse::from)
            .collect(Collectors.toList());
    }
}

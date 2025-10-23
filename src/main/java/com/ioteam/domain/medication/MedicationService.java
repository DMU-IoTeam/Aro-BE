package com.ioteam.domain.medication;

import com.ioteam.domain.medication.dto.*;
import com.ioteam.domain.medication.entity.MedicationItem;
import com.ioteam.domain.medication.entity.MedicationLog;
import com.ioteam.domain.medication.entity.MedicationSchedule;
import com.ioteam.domain.medication.repository.MedicationItemRepository;
import com.ioteam.domain.medication.repository.MedicationLogRepository;
import com.ioteam.domain.medication.repository.MedicationScheduleRepository;
import com.ioteam.domain.user.entity.User;
import com.ioteam.domain.user.repository.UserRepository;
import com.ioteam.global.exception.EntityNotFoundException;
import com.ioteam.global.exception.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicationService {

    private final MedicationScheduleRepository scheduleRepository;
    private final MedicationItemRepository itemRepository;
    private final MedicationLogRepository logRepository;
    private final UserRepository userRepository;

    private User getUserOrThrow(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다. (id=" + id + ")"));
    }

    private MedicationSchedule getScheduleOrThrow(Long id) {
        return scheduleRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("복약 일정을 찾을 수 없습니다. (id=" + id + ")"));
    }

    private void checkGuardianPermission(MedicationSchedule schedule, Long guardianId) {
        if (schedule.getCreatedBy() == null || !schedule.getCreatedBy().getId().equals(guardianId)) {
            throw new ForbiddenException("수정/삭제 권한이 없습니다.");
        }
    }

    @Transactional
    public MedicationScheduleResponse createSchedule(MedicationScheduleRequest request, Long guardianId) {
        User senior = getUserOrThrow(request.getUserId());
        User guardian = getUserOrThrow(guardianId);

        MedicationSchedule schedule = MedicationSchedule.builder()
            .user(senior)
            .createdBy(guardian)
            .time(request.getTime())
            .build();

        List<MedicationItem> items = request.getItems().stream()
            .map(i -> MedicationItem.builder().schedule(schedule).name(i.getName()).memo(i.getMemo()).build())
            .collect(Collectors.toList());

        schedule.setItems(items);
        MedicationSchedule savedSchedule = scheduleRepository.save(schedule);

        return new MedicationScheduleResponse(
            savedSchedule.getId(),
            savedSchedule.getUser().getId(),
            savedSchedule.getTime(),
            savedSchedule.getItems().stream().map(i -> new MedicationItemResponse(i.getId(), i.getName(), i.getMemo())).toList()
        );
    }

    @Transactional(readOnly = true)
    public List<MedicationScheduleResponse> getSchedulesBySenior(Long seniorId) {
        List<MedicationSchedule> schedules = scheduleRepository.findByUserIdWithItems(seniorId);
        return schedules.stream().map(s -> new MedicationScheduleResponse(
            s.getId(),
            s.getUser().getId(),
            s.getTime(),
            s.getItems().stream().map(i -> new MedicationItemResponse(i.getId(), i.getName(), i.getMemo())).toList()
        )).collect(Collectors.toList());
    }

    @Transactional
    public MedicationScheduleResponse updateSchedule(Long scheduleId, MedicationScheduleRequest request, Long guardianId) {
        MedicationSchedule schedule = getScheduleOrThrow(scheduleId);
        checkGuardianPermission(schedule, guardianId);

        List<MedicationItem> updatedItems = request.getItems().stream()
            .map(i -> MedicationItem.builder()
                .schedule(schedule)
                .name(i.getName())
                .memo(i.getMemo())
                .build())
            .collect(Collectors.toList());

        schedule.updateItems(updatedItems);
        schedule.changeTime(request.getTime());

        return new MedicationScheduleResponse(
            schedule.getId(),
            schedule.getUser().getId(),
            schedule.getTime(),
            schedule.getItems().stream()
                .map(i -> new MedicationItemResponse(i.getId(), i.getName(), i.getMemo()))
                .toList()
        );
    }

    @Transactional
    public void deleteSchedule(Long scheduleId, Long guardianId) {
        MedicationSchedule schedule = getScheduleOrThrow(scheduleId);
        checkGuardianPermission(schedule, guardianId);
        scheduleRepository.delete(schedule);
    }

    @Transactional
    public MedicationLogResponse confirmMedication(MedicationLogRequest request, Long userId) {
        MedicationSchedule schedule = getScheduleOrThrow(request.getScheduleId());
        User user = getUserOrThrow(userId);

        MedicationLog log = MedicationLog.builder()
            .schedule(schedule)
            .user(user)
            .confirmedAt(request.getConfirmedAt())
            .isNotified(false)
            .build();

        MedicationLog saved = logRepository.save(log);
        return new MedicationLogResponse(
            saved.getId(),
            saved.getSchedule().getId(),
            saved.getUser().getId(),
            saved.getConfirmedAt(),
            saved.isNotified(),
            saved.getCreatedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<MedicationLogResponse> getLogsBySenior(Long seniorId) {
        List<MedicationLog> logs = logRepository.findByUserId(seniorId);
        return logs.stream()
            .map(log -> new MedicationLogResponse(
                log.getId(),
                log.getSchedule().getId(),
                log.getUser().getId(),
                log.getConfirmedAt(),
                log.isNotified(),
                log.getCreatedAt()
            ))
            .collect(Collectors.toList());
    }
}
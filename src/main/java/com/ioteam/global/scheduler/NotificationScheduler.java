package com.ioteam.global.scheduler;

import com.ioteam.domain.medication.entity.MedicationSchedule;
import com.ioteam.domain.medication.repository.MedicationScheduleRepository;
import com.ioteam.domain.schedule.entity.Schedule;
import com.ioteam.domain.schedule.repository.ScheduleRepository;
import com.ioteam.domain.user.entity.User;
import com.ioteam.global.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {

    private final MedicationScheduleRepository medicationScheduleRepository;
    private final ScheduleRepository scheduleRepository;
    private final NotificationService notificationService;

    @Scheduled(cron = "0 * * * * *")
    public void sendMedicationNotifications() {
        LocalTime now = LocalTime.now().withSecond(0).withNano(0);
        List<MedicationSchedule> schedules = medicationScheduleRepository.findAllByTime(now);

        for (MedicationSchedule schedule : schedules) {
            User senior = schedule.getUser();
            if (senior != null && senior.getFirebaseToken() != null) {
                String message = senior.getName() + " 님, 약 드실 시간입니다.";
                notificationService.sendPushNotification(
                    senior.getFirebaseToken(), "복약 알림", message);
            }
        }
    }

    @Scheduled(cron = "0 * * * * *")
    public void sendScheduleNotifications() {
        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
        LocalDateTime oneMinuteLater = now.plusMinutes(1);
        List<Schedule> schedules = scheduleRepository.findAllByStartTimeBetween(now, oneMinuteLater);

        for (Schedule schedule : schedules) {
            User senior = schedule.getSenior();
            if (senior != null && senior.getFirebaseToken() != null) {
                String message = senior.getName() + " 님, 곧 '" + schedule.getTitle() + "' 일정이 있습니다.";
                notificationService.sendPushNotification(
                    senior.getFirebaseToken(), "일정 알림", message);
            }
        }
    }
}
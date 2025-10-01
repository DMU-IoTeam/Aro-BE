package com.ioteam.domain.schedule.dto;
import com.ioteam.domain.schedule.entity.Schedule;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ScheduleResponse {
    private Long id;
    private String title;
    private String memo;
    private LocalDateTime startTime;

    public ScheduleResponse(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.memo = schedule.getMemo();
        this.startTime = schedule.getStartTime();
    }
}
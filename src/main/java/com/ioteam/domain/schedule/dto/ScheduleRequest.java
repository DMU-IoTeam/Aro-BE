package com.ioteam.domain.schedule.dto;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ScheduleRequest {
    private Long seniorId;
    private String title;
    private String memo;
    private LocalDateTime startTime;
}
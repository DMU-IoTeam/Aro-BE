package com.ioteam.domain.medication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class MedicationScheduleResponse {
    private Long scheduleId;
    private Long userId;
    private LocalTime time;
    private List<MedicationItemResponse> items;
}
package com.ioteam.domain.medication.dto;

import lombok.Getter;
import java.time.LocalTime;
import java.util.List;

@Getter
public class MedicationScheduleRequest {
    private Long userId;
    private LocalTime time;
    private List<MedicationItemRequest> items;
}
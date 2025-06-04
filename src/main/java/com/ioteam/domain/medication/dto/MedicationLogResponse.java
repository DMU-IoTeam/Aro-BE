package com.ioteam.domain.medication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MedicationLogResponse {
    private Long logId;
    private Long scheduleId;
    private Long userId;
    private LocalDateTime confirmedAt;
    private boolean notified;
    private LocalDateTime createdAt;
}

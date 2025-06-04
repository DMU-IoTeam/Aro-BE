package com.ioteam.domain.medication.dto;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class MedicationLogRequest {
    private Long scheduleId;
    private LocalDateTime confirmedAt;
}

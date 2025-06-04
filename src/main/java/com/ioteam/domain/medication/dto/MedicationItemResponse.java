package com.ioteam.domain.medication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MedicationItemResponse {
    private Long id;
    private String name;
    private String memo;
}

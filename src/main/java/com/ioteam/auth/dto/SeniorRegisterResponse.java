package com.ioteam.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SeniorRegisterResponse {
    private Long id;
    private String name;
    private String birthDate;
    private String gender;
    private String address;
    private String medicalHistory;
    private String bloodType;
    private String profileImage;
}

package com.ioteam.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeniorRegisterRequest {
    private String name;
    private String email;
    private String birthDate;
    private String gender;
    private String address;
    private String medicalHistory;
    private String bloodType;
    private String profileImage;
}

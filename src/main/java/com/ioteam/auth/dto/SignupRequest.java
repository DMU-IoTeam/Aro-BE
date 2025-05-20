package com.ioteam.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String birthDate;
    private String gender;
    private String address;
    private String profileImage;
}

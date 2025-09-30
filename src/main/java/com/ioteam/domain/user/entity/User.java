package com.ioteam.domain.user.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "user")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long kakaoId;

    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String email;

    private String phone;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String address;

    private String profileImage;

    private String medicalHistory;
    private String bloodType;

    private String firebaseToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guardian_id")
    private User guardian;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static User createKakaoUser(Long kakaoId, String name, String email) {
        return User.builder()
            .kakaoId(kakaoId)
            .name(name)
            .email(email)
            .role(Role.GUARDIAN)
            .build();
    }

    @PrePersist
    protected void onCreate() {
        createdAt = updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum Role {
        SENIOR, GUARDIAN
    }

    public enum Gender {
        MALE, FEMALE, OTHER
    }

    public void updateSeniorInfo(String name, LocalDate birthDate, Gender gender,
        String address, String medicalHistory, String bloodType, String profileImage) {
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.address = address;
        this.medicalHistory = medicalHistory;
        this.bloodType = bloodType;
        this.profileImage = profileImage;
    }

    public void updateFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }
}
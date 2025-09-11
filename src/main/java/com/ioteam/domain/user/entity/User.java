package com.ioteam.domain.user.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(unique = true)
    private String email;

    private String password;

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

    public void encodePassword(PasswordEncoder encoder) {
        this.password = encoder.encode(this.password);
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

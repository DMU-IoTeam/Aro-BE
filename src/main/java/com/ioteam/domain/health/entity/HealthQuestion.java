package com.ioteam.domain.health.entity;

import com.ioteam.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class HealthQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guardian_id")
    private User guardian;

    @Column(nullable = false)
    private String questionText;

    @Column(nullable = false)
    private String options;

    public void update(String questionText, String options) {
        this.questionText = questionText;
        this.options = options;
    }
}
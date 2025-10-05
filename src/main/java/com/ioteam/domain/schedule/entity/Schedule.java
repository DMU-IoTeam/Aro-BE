package com.ioteam.domain.schedule.entity;

import com.ioteam.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "senior_id")
    private User senior;

    @Column(nullable = false)
    private String title;

    @Lob
    private String memo;

    @Column(nullable = false)
    private LocalDateTime startTime;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public void update(String title, String memo, LocalDateTime startTime) {
        this.title = title;
        this.memo = memo;
        this.startTime = startTime;
    }
}
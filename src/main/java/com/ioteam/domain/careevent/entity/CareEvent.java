package com.ioteam.domain.careevent.entity;

import com.ioteam.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "care_event")
public class CareEvent {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "senior_id", nullable = false)
    private User senior;

    @Enumerated(EnumType.STRING)
    private EventType type;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime occurredAt;

    @PrePersist
    protected void onCreate() {
        occurredAt = LocalDateTime.now();
        if (status == null) status = Status.DETECTED;
    }

    public enum EventType {
        FALL, INTRUSION
    }

    public enum Status {
        DETECTED, NOTIFIED, ACKED, RESOLVED
    }

    public void ack() {
        this.status = Status.ACKED;
    }
}

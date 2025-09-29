package com.ioteam.domain.game.entity;

import com.ioteam.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GameResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "senior_id")
    private User senior;

    @Enumerated(EnumType.STRING)
    private GameType gameType;

    private Integer score;
    private Integer totalQuestions;

    private LocalDateTime playedAt;

    public enum GameType {
        FACE_MATCH
    }

    @PrePersist
    protected void onCreate() {
        playedAt = LocalDateTime.now();
    }
}
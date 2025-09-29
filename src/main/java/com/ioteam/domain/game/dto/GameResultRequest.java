package com.ioteam.domain.game.dto;

import com.ioteam.domain.game.entity.GameResult;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameResultRequest {
    private Long seniorId;
    private GameResult.GameType gameType;
    private Integer score;
    private Integer totalQuestions;
}
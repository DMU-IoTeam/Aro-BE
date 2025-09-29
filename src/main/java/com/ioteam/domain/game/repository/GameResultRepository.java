package com.ioteam.domain.game.repository;

import com.ioteam.domain.game.entity.GameResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameResultRepository extends JpaRepository<GameResult, Long> {
}
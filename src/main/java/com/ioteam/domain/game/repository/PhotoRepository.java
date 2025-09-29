package com.ioteam.domain.game.repository;

import com.ioteam.domain.game.entity.Photo;
import com.ioteam.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findAllByGuardian(User guardian);
}
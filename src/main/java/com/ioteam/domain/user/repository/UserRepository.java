package com.ioteam.domain.user.repository;

import com.ioteam.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByGuardianIdAndRole(Long guardianId, User.Role role);
}

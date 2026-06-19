package com.omkar.jobaiassistant.repository;

import com.omkar.jobaiassistant.entity.User;
import com.omkar.jobaiassistant.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {

    List<UserSession> findByUserAndActiveTrue(User user);

    Optional<UserSession> findByRefreshToken(String refreshToken);

    void deleteByUser(User user);
}
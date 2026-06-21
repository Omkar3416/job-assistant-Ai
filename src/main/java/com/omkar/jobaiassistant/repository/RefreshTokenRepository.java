package com.omkar.jobaiassistant.repository;

import com.omkar.jobaiassistant.entity.RefreshToken;
import com.omkar.jobaiassistant.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUser(User user);

    @Transactional
    void deleteByUser(User user);
}
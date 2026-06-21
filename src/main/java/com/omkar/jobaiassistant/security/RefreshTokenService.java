package com.omkar.jobaiassistant.security;

import com.omkar.jobaiassistant.entity.RefreshToken;
import com.omkar.jobaiassistant.entity.User;
import com.omkar.jobaiassistant.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public RefreshToken createRefreshToken(
            User user,
            int validityDays
    ) {

        RefreshToken token =
                refreshTokenRepository
                        .findByUser(user)
                        .orElse(new RefreshToken());

        token.setUser(user);

        token.setToken(
                UUID.randomUUID().toString()
        );

        token.setExpiryDate(
                LocalDateTime.now().plusDays(validityDays)
        );

        return refreshTokenRepository.save(token);
    }

    public RefreshToken validate(String token) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (refreshToken.isExpired()) {
            throw new RuntimeException("Refresh token expired");
        }

        return refreshToken;
    }

    @Transactional
    public void delete(RefreshToken token) {
        refreshTokenRepository.delete(token);
    }

    @Transactional
    public void deleteByUser(User user) {
        refreshTokenRepository.deleteByUser(user);
    }
}
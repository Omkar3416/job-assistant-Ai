package com.omkar.jobaiassistant.security;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

@Service
public class TokenBlacklistService {

    private final RedisTemplate<String, String> redisTemplate;

    public TokenBlacklistService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 🔐 convert token → hash key
    private String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Error hashing token", e);
        }
    }

    // 🚫 store blacklisted token
    public void blacklist(String token, long expirationMillis) {

        String key = hashToken(token);

        redisTemplate.opsForValue().set(
                key,
                "blacklisted",
                expirationMillis,
                TimeUnit.MILLISECONDS
        );
    }

    // 🔍 check blacklist
    public boolean isBlacklisted(String token) {

        String key = hashToken(token);

        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(key));
        } catch (Exception e) {
            System.out.println("Redis down → skipping blacklist check");
            return false;
        }
    }
}
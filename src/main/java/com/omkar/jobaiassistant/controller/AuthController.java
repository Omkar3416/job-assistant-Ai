package com.omkar.jobaiassistant.controller;

import com.omkar.jobaiassistant.dto.*;
import com.omkar.jobaiassistant.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(
            @Valid @RequestBody RegisterRequestDto request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(
            @Valid @RequestBody LoginRequestDto request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDto> refresh(
            @RequestBody RefreshTokenRequestDto request
    ) {
        return ResponseEntity.ok(
                authService.refreshToken(
                        request.getRefreshToken()
                )
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponseDto> logout(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody RefreshTokenRequestDto request
    ) {

        String accessToken = authHeader.substring(7);

        authService.logout(
                request.getRefreshToken(),
                accessToken
        );

        LogoutResponseDto response =
                new LogoutResponseDto();

        response.setSuccess(true);
        response.setMessage("Logged out successfully");

        return ResponseEntity.ok(response);
    }
}
package com.omkar.jobaiassistant.service;

import com.omkar.jobaiassistant.dto.*;

public interface AuthService {

    RegisterResponseDto register(RegisterRequestDto request);

    AuthResponseDto login(LoginRequestDto request);

    AuthResponseDto refreshToken(String refreshToken);

    void logout(String refreshToken, String accessToken);

    AuthResponseDto googleLogin(String idToken);
}
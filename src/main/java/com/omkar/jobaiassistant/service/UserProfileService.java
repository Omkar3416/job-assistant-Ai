package com.omkar.jobaiassistant.service;

import com.omkar.jobaiassistant.dto.UserProfileRequestDto;
import com.omkar.jobaiassistant.dto.UserProfileResponseDto;

public interface UserProfileService {

    UserProfileResponseDto createProfile(
            UserProfileRequestDto request
    );

    UserProfileResponseDto getProfile(String email);
}
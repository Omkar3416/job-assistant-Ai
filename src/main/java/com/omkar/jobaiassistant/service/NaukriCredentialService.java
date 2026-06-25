package com.omkar.jobaiassistant.service;

import com.omkar.jobaiassistant.dto.NaukriCredentialRequestDto;
import com.omkar.jobaiassistant.dto.NaukriCredentialResponseDto;

public interface NaukriCredentialService {

    NaukriCredentialResponseDto saveCredentials(
            String email,
            NaukriCredentialRequestDto request
    );

    NaukriCredentialResponseDto getCredentials(
            String email
    );

    String getDecryptedPassword(
            String email
    );
}
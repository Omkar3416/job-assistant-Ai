package com.omkar.jobaiassistant.service;

import com.omkar.jobaiassistant.dto.JobPreferenceRequestDto;
import com.omkar.jobaiassistant.dto.JobPreferenceResponseDto;

public interface JobPreferenceService {

    JobPreferenceResponseDto savePreference(
            JobPreferenceRequestDto request
    );

    JobPreferenceResponseDto getPreference(
            String email
    );
}
package com.omkar.jobaiassistant.service;

import com.omkar.jobaiassistant.dto.JobMatchCheckRequestDto;
import com.omkar.jobaiassistant.dto.JobMatchCheckResponseDto;

public interface PlaywrightAutomationService {

    JobMatchCheckResponseDto checkJob(
            JobMatchCheckRequestDto request
    );

}
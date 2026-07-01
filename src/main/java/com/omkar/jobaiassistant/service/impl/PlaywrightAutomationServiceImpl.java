package com.omkar.jobaiassistant.service.impl;

import com.omkar.jobaiassistant.dto.JobMatchCheckRequestDto;
import com.omkar.jobaiassistant.dto.JobMatchCheckResponseDto;
import com.omkar.jobaiassistant.service.PlaywrightAutomationService;
import org.springframework.stereotype.Service;

@Service
public class PlaywrightAutomationServiceImpl
        implements PlaywrightAutomationService {

    @Override
    public JobMatchCheckResponseDto checkJob(
            JobMatchCheckRequestDto request
    ) {

        JobMatchCheckResponseDto response =
                new JobMatchCheckResponseDto();

        response.setMatched(true);

        response.setExternalApply(false);

        response.setReason(
                "Temporary implementation"
        );

        return response;

    }

}
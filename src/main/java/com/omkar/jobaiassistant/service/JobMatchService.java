package com.omkar.jobaiassistant.service;

import com.omkar.jobaiassistant.dto.MatchScoreResponseDto;

public interface JobMatchService {

    MatchScoreResponseDto calculateMatchScore(
            Long jobId,
            String email
    );
}
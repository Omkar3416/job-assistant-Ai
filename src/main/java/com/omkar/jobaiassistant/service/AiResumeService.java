package com.omkar.jobaiassistant.service;

import com.omkar.jobaiassistant.dto.MatchScoreResponseDto;

public interface AiResumeService {

    String generateResumeSummary(
            String resumeText
    );

    MatchScoreResponseDto calculateMatchScore(
            String resumeText,
            String jobDescription
    );
}
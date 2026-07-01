package com.omkar.jobaiassistant.service;

import com.omkar.jobaiassistant.dto.MatchScoreResponseDto;

public interface AiResumeService {

    String generateResumeSummary(
            String resumeText
    );

    String generateSearchKeywords(
            String resumeText,
            String skills,
            String preferredRoles,
            String preferredLocations
    );

    MatchScoreResponseDto calculateMatchScore(
            String resumeText,
            String jobDescription
    );
}
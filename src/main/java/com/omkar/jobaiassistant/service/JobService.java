package com.omkar.jobaiassistant.service;

import com.omkar.jobaiassistant.dto.JobRequestDto;
import com.omkar.jobaiassistant.dto.JobResponseDto;

import java.util.List;

public interface JobService {

    List<JobResponseDto> getAllJobs();

    List<JobResponseDto> searchJobs(
            String keyword
    );

    List<JobResponseDto> getRecommendedJobs(
            String email
    );

    JobResponseDto createJob(
            JobRequestDto request
    );

    JobResponseDto getJobById(
            Long id
    );

    JobResponseDto updateJob(
            Long id,
            JobRequestDto request
    );

    void deleteJob(
            Long id
    );
}
package com.omkar.jobaiassistant.service;

import com.omkar.jobaiassistant.dto.JobApplicationResponseDto;
import com.omkar.jobaiassistant.dto.JobDashboardResponseDto;
import com.omkar.jobaiassistant.entity.ApplicationStatus;

import java.util.List;

public interface JobApplicationService {

    JobApplicationResponseDto applyJob(
            Long jobId,
            String email
    );

    List<JobApplicationResponseDto> getMyApplications(
            String email
    );

    JobDashboardResponseDto getDashboard(
            String email
    );
    JobApplicationResponseDto updateStatus(
            Long applicationId,
            ApplicationStatus status
    );
}
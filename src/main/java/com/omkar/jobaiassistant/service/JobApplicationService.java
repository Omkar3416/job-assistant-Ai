package com.omkar.jobaiassistant.service;

import com.omkar.jobaiassistant.dto.JobApplicationResponseDto;
import com.omkar.jobaiassistant.dto.JobDashboardResponseDto;
import com.omkar.jobaiassistant.dto.JobMatchCheckRequestDto;
import com.omkar.jobaiassistant.dto.JobMatchCheckResponseDto;
import com.omkar.jobaiassistant.entity.ApplicationStatus;
import com.omkar.jobaiassistant.dto.ApplyResultRequestDto;
import com.omkar.jobaiassistant.dto.ApplyJobResultDto;
import com.omkar.jobaiassistant.entity.User;
import com.omkar.jobaiassistant.dto.NaukriJobDto;

import java.util.List;


public interface JobApplicationService {

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



    void searchAndApplyJobs(
            String email
    );

    void searchAndApplyJobsForAllUsers();

    boolean isEligibleForAutoApply(
            User user,
            NaukriJobDto job
    );

    JobMatchCheckResponseDto canApply(
            JobMatchCheckRequestDto request
    );

    ApplyJobResultDto applyJob(
            Long jobId,
            String email
    );
    void saveExternalApplication(
            User user,
            NaukriJobDto job
    );
    void applyResult(
            ApplyResultRequestDto request
    );
}
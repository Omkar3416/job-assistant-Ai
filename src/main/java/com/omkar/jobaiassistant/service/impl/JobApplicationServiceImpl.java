package com.omkar.jobaiassistant.service.impl;

import com.omkar.jobaiassistant.dto.JobApplicationResponseDto;
import com.omkar.jobaiassistant.entity.ApplicationStatus;
import com.omkar.jobaiassistant.entity.Job;
import com.omkar.jobaiassistant.entity.JobApplication;
import com.omkar.jobaiassistant.entity.User;
import com.omkar.jobaiassistant.repository.JobApplicationRepository;
import com.omkar.jobaiassistant.repository.JobRepository;
import com.omkar.jobaiassistant.repository.UserRepository;
import com.omkar.jobaiassistant.service.JobApplicationService;
import org.springframework.stereotype.Service;
import com.omkar.jobaiassistant.dto.JobDashboardResponseDto;
import com.omkar.jobaiassistant.dto.UpdateApplicationStatusRequestDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobApplicationServiceImpl
        implements JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;

    private final JobRepository jobRepository;

    private final UserRepository userRepository;

    public JobApplicationServiceImpl(
            JobApplicationRepository jobApplicationRepository,
            JobRepository jobRepository,
            UserRepository userRepository
    ) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    @Override
    public JobApplicationResponseDto applyJob(
            Long jobId,
            String email
    ) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        Job job =
                jobRepository.findById(jobId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Job not found"
                                )
                        );

        boolean alreadyApplied =
                jobApplicationRepository
                        .existsByUserIdAndCompanyNameAndJobTitle(
                                user.getId(),
                                job.getCompanyName(),
                                job.getTitle()
                        );

        if (alreadyApplied) {
            throw new RuntimeException(
                    "Already applied for this job"
            );
        }

        JobApplication application =
                new JobApplication();

        application.setCompanyName(
                job.getCompanyName()
        );

        application.setJobTitle(
                job.getTitle()
        );

        application.setJobUrl(
                job.getJobUrl()
        );

        application.setMatchScore(
                0.0
        );

        application.setStatus(
                ApplicationStatus.APPLIED
        );

        application.setUser(user);

        JobApplication saved =
                jobApplicationRepository
                        .save(application);

        return map(saved,
                "Job application submitted successfully");
    }

    @Override
    public List<JobApplicationResponseDto>
    getMyApplications(
            String email
    ) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        List<JobApplication> applications =
                jobApplicationRepository
                        .findByUserId(
                                user.getId()
                        );

        List<JobApplicationResponseDto> responses =
                new ArrayList<>();

        for (JobApplication application : applications) {
            responses.add(
                    map(application, null)
            );
        }

        return responses;
    }

    @Override
    public JobDashboardResponseDto getDashboard(
            String email
    ) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        List<JobApplication> applications =
                jobApplicationRepository
                        .findByUserId(
                                user.getId()
                        );

        long totalApplications =
                applications.size();

        long appliedCount =
                applications.stream()
                        .filter(application ->
                                application.getStatus()
                                        == ApplicationStatus.APPLIED
                        )
                        .count();

        long failedCount =
                applications.stream()
                        .filter(application ->
                                application.getStatus()
                                        == ApplicationStatus.FAILED
                        )
                        .count();

        long waitingCount =
                applications.stream()
                        .filter(application ->
                                application.getStatus()
                                        == ApplicationStatus.WAITING_FOR_ANSWER
                        )
                        .count();

        JobDashboardResponseDto response =
                new JobDashboardResponseDto();

        response.setTotalApplications(
                totalApplications
        );

        response.setAppliedCount(
                appliedCount
        );

        response.setFailedCount(
                failedCount
        );

        response.setWaitingCount(
                waitingCount
        );

        return response;
    }

    @Override
    public JobApplicationResponseDto updateStatus(
            Long applicationId,
            ApplicationStatus status
    ) {

        JobApplication application =
                jobApplicationRepository
                        .findById(applicationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Application not found"
                                )
                        );

        application.setStatus(status);

        JobApplication updated =
                jobApplicationRepository.save(
                        application
                );

        return map(
                updated,
                "Application status updated successfully"
        );
    }

    private JobApplicationResponseDto map(
            JobApplication application,
            String message
    ) {

        JobApplicationResponseDto dto =
                new JobApplicationResponseDto();

        dto.setId(
                application.getId()
        );

        dto.setCompanyName(
                application.getCompanyName()
        );

        dto.setJobTitle(
                application.getJobTitle()
        );

        dto.setJobUrl(
                application.getJobUrl()
        );

        dto.setMatchScore(
                application.getMatchScore()
        );

        dto.setStatus(
                application.getStatus()
        );

        dto.setAppliedAt(
                application.getAppliedAt()
        );

        dto.setMessage(
                message
        );

        return dto;
    }
}
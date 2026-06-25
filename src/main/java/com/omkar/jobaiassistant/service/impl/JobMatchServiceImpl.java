package com.omkar.jobaiassistant.service.impl;

import com.omkar.jobaiassistant.dto.MatchScoreResponseDto;
import com.omkar.jobaiassistant.entity.Job;
import com.omkar.jobaiassistant.entity.Resume;
import com.omkar.jobaiassistant.entity.User;
import com.omkar.jobaiassistant.repository.JobRepository;
import com.omkar.jobaiassistant.repository.ResumeRepository;
import com.omkar.jobaiassistant.repository.UserRepository;
import com.omkar.jobaiassistant.service.AiResumeService;
import com.omkar.jobaiassistant.service.JobMatchService;
import org.springframework.stereotype.Service;

@Service
public class JobMatchServiceImpl
        implements JobMatchService {

    private final JobRepository jobRepository;
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final AiResumeService aiResumeService;

    public JobMatchServiceImpl(
            JobRepository jobRepository,
            ResumeRepository resumeRepository,
            UserRepository userRepository,
            AiResumeService aiResumeService
    ) {
        this.jobRepository = jobRepository;
        this.resumeRepository = resumeRepository;
        this.userRepository = userRepository;
        this.aiResumeService = aiResumeService;
    }

    @Override
    public MatchScoreResponseDto calculateMatchScore(
            Long jobId,
            String email
    ) {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        Resume resume =
                resumeRepository
                        .findByUserId(
                                user.getId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Resume not found"
                                )
                        );

        Job job =
                jobRepository
                        .findById(jobId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Job not found"
                                )
                        );

        return aiResumeService
                .calculateMatchScore(
                        resume.getExtractedText(),
                        job.getDescription()
                );
    }
}
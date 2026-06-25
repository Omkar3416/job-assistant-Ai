package com.omkar.jobaiassistant.controller;

import com.omkar.jobaiassistant.dto.JobApplicationResponseDto;
import com.omkar.jobaiassistant.service.JobApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.omkar.jobaiassistant.dto.JobDashboardResponseDto;
import com.omkar.jobaiassistant.dto.UpdateApplicationStatusRequestDto;
import com.omkar.jobaiassistant.entity.ApplicationStatus;

import java.util.List;

@RestController
@RequestMapping("/api/job-applications")
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    public JobApplicationController(
            JobApplicationService jobApplicationService
    ) {
        this.jobApplicationService = jobApplicationService;
    }

    @PostMapping("/apply/{jobId}")
    public ResponseEntity<JobApplicationResponseDto>
    applyJob(
            @PathVariable Long jobId,
            Authentication authentication
    ) {

        String email =
                authentication.getName();

        return ResponseEntity.ok(
                jobApplicationService.applyJob(
                        jobId,
                        email
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<JobApplicationResponseDto>>
    getMyApplications(
            Authentication authentication
    ) {

        String email =
                authentication.getName();

        return ResponseEntity.ok(
                jobApplicationService.getMyApplications(
                        email
                )
        );
    }
    @GetMapping("/dashboard")
    public ResponseEntity<JobDashboardResponseDto>
    getDashboard(
            Authentication authentication
    ) {

        String email =
                authentication.getName();

        return ResponseEntity.ok(
                jobApplicationService.getDashboard(
                        email
                )
        );
    }
    @PutMapping("/{applicationId}/status")
    public ResponseEntity<JobApplicationResponseDto>
    updateStatus(
            @PathVariable Long applicationId,
            @RequestBody
            UpdateApplicationStatusRequestDto request
    ) {

        return ResponseEntity.ok(
                jobApplicationService.updateStatus(
                        applicationId,
                        request.getStatus()
                )
        );
    }
}
package com.omkar.jobaiassistant.controller;

import com.omkar.jobaiassistant.dto.JobApplicationResponseDto;
import com.omkar.jobaiassistant.service.JobApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.omkar.jobaiassistant.dto.JobDashboardResponseDto;
import com.omkar.jobaiassistant.dto.UpdateApplicationStatusRequestDto;
import com.omkar.jobaiassistant.entity.ApplicationStatus;
import com.omkar.jobaiassistant.dto.JobMatchCheckRequestDto;
import com.omkar.jobaiassistant.dto.JobMatchCheckResponseDto;
import com.omkar.jobaiassistant.dto.ApplyResultRequestDto;

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
    @PostMapping("/can-apply")
    public ResponseEntity<JobMatchCheckResponseDto> canApply(
            @RequestBody
            JobMatchCheckRequestDto request
    ) {

        return ResponseEntity.ok(
                jobApplicationService.canApply(request)
        );

    }
    @PostMapping("/apply-result")
    public ResponseEntity<Void> applyResult(
            @RequestBody
            ApplyResultRequestDto request
    ) {

        jobApplicationService.applyResult(request);

        return ResponseEntity.ok().build();

    }

}
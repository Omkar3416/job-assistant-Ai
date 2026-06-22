package com.omkar.jobaiassistant.controller;

import com.omkar.jobaiassistant.dto.JobPreferenceRequestDto;
import com.omkar.jobaiassistant.dto.JobPreferenceResponseDto;
import com.omkar.jobaiassistant.service.JobPreferenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/job-preferences")
public class JobPreferenceController {

    private final JobPreferenceService jobPreferenceService;

    public JobPreferenceController(
            JobPreferenceService jobPreferenceService
    ) {
        this.jobPreferenceService = jobPreferenceService;
    }

    @PostMapping
    public ResponseEntity<JobPreferenceResponseDto>
    savePreference(
            @RequestBody
            JobPreferenceRequestDto request
    ) {

        return ResponseEntity.ok(
                jobPreferenceService
                        .savePreference(request)
        );
    }

    @GetMapping
    public ResponseEntity<JobPreferenceResponseDto>
    getPreference(
            Authentication authentication
    ) {

        String email =
                authentication.getName();

        return ResponseEntity.ok(
                jobPreferenceService
                        .getPreference(email)
        );
    }
}
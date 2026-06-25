package com.omkar.jobaiassistant.controller;

import com.omkar.jobaiassistant.dto.MatchScoreResponseDto;
import com.omkar.jobaiassistant.service.JobMatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/job-match")
public class JobMatchController {

    private final JobMatchService jobMatchService;

    public JobMatchController(
            JobMatchService jobMatchService
    ) {
        this.jobMatchService = jobMatchService;
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<MatchScoreResponseDto>
    calculateMatchScore(
            @PathVariable Long jobId,
            Authentication authentication
    ) {

        String email =
                authentication.getName();

        return ResponseEntity.ok(
                jobMatchService
                        .calculateMatchScore(
                                jobId,
                                email
                        )
        );
    }
}
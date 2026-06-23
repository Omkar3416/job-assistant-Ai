package com.omkar.jobaiassistant.controller;

import com.omkar.jobaiassistant.dto.JobResponseDto;
import com.omkar.jobaiassistant.service.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.omkar.jobaiassistant.dto.JobRequestDto;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(
            JobService jobService
    ) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<JobResponseDto>>
    getAllJobs() {

        return ResponseEntity.ok(
                jobService.getAllJobs()
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<JobResponseDto>>
    searchJobs(
            @RequestParam String keyword
    ) {

        return ResponseEntity.ok(
                jobService.searchJobs(keyword)
        );
    }

    @GetMapping("/recommendations")
    public ResponseEntity<List<JobResponseDto>>
    getRecommendations(
            Authentication authentication
    ) {

        String email =
                authentication.getName();

        return ResponseEntity.ok(
                jobService.getRecommendedJobs(email)
        );
    }
    @PostMapping
    public ResponseEntity<JobResponseDto>
    createJob(
            @RequestBody JobRequestDto request
    ) {

        return ResponseEntity.ok(
                jobService.createJob(request)
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<JobResponseDto>
    getJobById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                jobService.getJobById(id)
        );
    }
    @PutMapping("/{id}")
    public ResponseEntity<JobResponseDto>
    updateJob(
            @PathVariable Long id,
            @RequestBody JobRequestDto request
    ) {

        return ResponseEntity.ok(
                jobService.updateJob(id, request)
        );
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String>
    deleteJob(
            @PathVariable Long id
    ) {

        jobService.deleteJob(id);

        return ResponseEntity.ok(
                "Job deleted successfully"
        );
    }
}
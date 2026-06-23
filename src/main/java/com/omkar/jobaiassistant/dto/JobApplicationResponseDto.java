package com.omkar.jobaiassistant.dto;

import com.omkar.jobaiassistant.entity.ApplicationStatus;

import java.time.LocalDateTime;

public class JobApplicationResponseDto {

    private Long id;

    private String companyName;

    private String jobTitle;

    private String jobUrl;

    private Double matchScore;

    private ApplicationStatus status;

    private LocalDateTime appliedAt;

    private String message;

    public JobApplicationResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getJobUrl() {
        return jobUrl;
    }

    public Double getMatchScore() {
        return matchScore;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    public String getMessage() {
        return message;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setJobUrl(String jobUrl) {
        this.jobUrl = jobUrl;
    }

    public void setMatchScore(Double matchScore) {
        this.matchScore = matchScore;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
package com.omkar.jobaiassistant.dto;

import com.omkar.jobaiassistant.entity.ApplicationStatus;

import java.time.LocalDateTime;

public class JobApplicationResponseDto {

    private Long id;

    private String companyName;

    private String jobTitle;

    private String jobUrl;

    private Double matchScore;

    private String sourcePortal;

    private Boolean autoApplied;

    private Boolean manualApplyRequired;

    private Integer attemptCount;

    private String lastError;

    private LocalDateTime queuedAt;

    private LocalDateTime completedAt;

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

    public String getSourcePortal() {
        return sourcePortal;
    }

    public Boolean getAutoApplied() {
        return autoApplied;
    }

    public Boolean getManualApplyRequired() {
        return manualApplyRequired;
    }

    public Integer getAttemptCount() {
        return attemptCount;
    }

    public String getLastError() {
        return lastError;
    }

    public LocalDateTime getQueuedAt() {
        return queuedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
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

    public void setSourcePortal(String sourcePortal) {
        this.sourcePortal = sourcePortal;
    }

    public void setAutoApplied(Boolean autoApplied) {
        this.autoApplied = autoApplied;
    }

    public void setManualApplyRequired(Boolean manualApplyRequired) {
        this.manualApplyRequired = manualApplyRequired;
    }

    public void setAttemptCount(Integer attemptCount) {
        this.attemptCount = attemptCount;
    }

    public void setLastError(String lastError) {
        this.lastError = lastError;
    }

    public void setQueuedAt(LocalDateTime queuedAt) {
        this.queuedAt = queuedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
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
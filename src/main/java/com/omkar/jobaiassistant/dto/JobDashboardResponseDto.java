package com.omkar.jobaiassistant.dto;

public class JobDashboardResponseDto {

    private Long totalApplications;

    private Long appliedCount;

    private Long failedCount;

    private Long waitingCount;

    public JobDashboardResponseDto() {
    }

    public Long getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(
            Long totalApplications
    ) {
        this.totalApplications = totalApplications;
    }

    public Long getAppliedCount() {
        return appliedCount;
    }

    public void setAppliedCount(
            Long appliedCount
    ) {
        this.appliedCount = appliedCount;
    }

    public Long getFailedCount() {
        return failedCount;
    }

    public void setFailedCount(
            Long failedCount
    ) {
        this.failedCount = failedCount;
    }

    public Long getWaitingCount() {
        return waitingCount;
    }

    public void setWaitingCount(
            Long waitingCount
    ) {
        this.waitingCount = waitingCount;
    }
}
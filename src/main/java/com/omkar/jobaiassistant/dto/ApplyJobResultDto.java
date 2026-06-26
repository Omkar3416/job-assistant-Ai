package com.omkar.jobaiassistant.dto;

public class ApplyJobResultDto {

    private boolean applied;

    private boolean quotaReached;

    private boolean externalApply;

    private String error;

    public boolean isApplied() {
        return applied;
    }

    public void setApplied(boolean applied) {
        this.applied = applied;
    }

    public boolean isQuotaReached() {
        return quotaReached;
    }

    public void setQuotaReached(boolean quotaReached) {
        this.quotaReached = quotaReached;
    }

    public boolean isExternalApply() {
        return externalApply;
    }

    public void setExternalApply(boolean externalApply) {
        this.externalApply = externalApply;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
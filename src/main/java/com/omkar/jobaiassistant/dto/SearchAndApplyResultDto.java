package com.omkar.jobaiassistant.dto;

public class SearchAndApplyResultDto {

    private boolean success;

    private int searchedJobs;

    private int matchedJobs;

    private int appliedJobs;

    private boolean quotaReached;

    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getSearchedJobs() {
        return searchedJobs;
    }

    public void setSearchedJobs(int searchedJobs) {
        this.searchedJobs = searchedJobs;
    }

    public int getMatchedJobs() {
        return matchedJobs;
    }

    public void setMatchedJobs(int matchedJobs) {
        this.matchedJobs = matchedJobs;
    }

    public int getAppliedJobs() {
        return appliedJobs;
    }

    public void setAppliedJobs(int appliedJobs) {
        this.appliedJobs = appliedJobs;
    }

    public boolean isQuotaReached() {
        return quotaReached;
    }

    public void setQuotaReached(boolean quotaReached) {
        this.quotaReached = quotaReached;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
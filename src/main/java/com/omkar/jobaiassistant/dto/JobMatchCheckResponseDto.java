package com.omkar.jobaiassistant.dto;

public class JobMatchCheckResponseDto {

    private boolean matched;

    private boolean externalApply;

    private String reason;

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public boolean isExternalApply() {
        return externalApply;
    }

    public void setExternalApply(boolean externalApply) {
        this.externalApply = externalApply;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
package com.omkar.jobaiassistant.dto;

import com.omkar.jobaiassistant.entity.ApplicationStatus;

public class UpdateApplicationStatusRequestDto {

    private ApplicationStatus status;

    public UpdateApplicationStatusRequestDto() {
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(
            ApplicationStatus status
    ) {
        this.status = status;
    }
}
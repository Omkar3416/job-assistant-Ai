package com.omkar.jobaiassistant.dto;

import lombok.Data;

@Data
public class ApplyResultRequestDto {

    private String email;

    private String title;

    private String company;

    private String jobUrl;

    private boolean applied;

    private boolean quotaReached;

    private boolean externalApply;

    private String error;

}
package com.omkar.jobaiassistant.dto;

public class NaukriCredentialResponseDto {

    private String naukriEmail;

    private boolean configured;

    public NaukriCredentialResponseDto() {
    }

    public String getNaukriEmail() {
        return naukriEmail;
    }

    public void setNaukriEmail(
            String naukriEmail
    ) {
        this.naukriEmail = naukriEmail;
    }

    public boolean isConfigured() {
        return configured;
    }

    public void setConfigured(
            boolean configured
    ) {
        this.configured = configured;
    }
}
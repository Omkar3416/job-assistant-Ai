package com.omkar.jobaiassistant.dto;

public class NaukriCredentialRequestDto {

    private String naukriEmail;

    private String naukriPassword;

    public NaukriCredentialRequestDto() {
    }

    public String getNaukriEmail() {
        return naukriEmail;
    }

    public void setNaukriEmail(String naukriEmail) {
        this.naukriEmail = naukriEmail;
    }

    public String getNaukriPassword() {
        return naukriPassword;
    }

    public void setNaukriPassword(
            String naukriPassword
    ) {
        this.naukriPassword = naukriPassword;
    }
}
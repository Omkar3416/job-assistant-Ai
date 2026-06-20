package com.omkar.jobaiassistant.dto;

public class GoogleLoginRequestDto {

    private String idToken;

    public GoogleLoginRequestDto() {
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
}
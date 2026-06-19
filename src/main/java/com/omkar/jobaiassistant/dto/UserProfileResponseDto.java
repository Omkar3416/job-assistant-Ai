package com.omkar.jobaiassistant.dto;

public class UserProfileResponseDto {

    private Long id;

    private String fullName;

    private String message;

    public UserProfileResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getMessage() {
        return message;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
package com.omkar.jobaiassistant.dto;

public class CurrentUserDto {

    private Long id;
    private String email;
    private String role;
    private String provider;

    public CurrentUserDto() {
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getProvider() {
        return provider;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
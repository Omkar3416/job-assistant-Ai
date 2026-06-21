package com.omkar.jobaiassistant.dto;

public class UserProfileResponseDto {

    private Long id;

    private String fullName;

    private String message;

    private String phone;

    private String currentRole;

    private String preferredRoles;

    private String currentCity;

    private String preferredCities;

    private Boolean remoteAllowed;

    private Boolean hybridAllowed;

    private Boolean officeAllowed;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCurrentRole() {
        return currentRole;
    }

    public void setCurrentRole(String currentRole) {
        this.currentRole = currentRole;
    }

    public String getPreferredRoles() {
        return preferredRoles;
    }

    public void setPreferredRoles(String preferredRoles) {
        this.preferredRoles = preferredRoles;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public String getPreferredCities() {
        return preferredCities;
    }

    public void setPreferredCities(String preferredCities) {
        this.preferredCities = preferredCities;
    }

    public Boolean getRemoteAllowed() {
        return remoteAllowed;
    }

    public void setRemoteAllowed(Boolean remoteAllowed) {
        this.remoteAllowed = remoteAllowed;
    }

    public Boolean getHybridAllowed() {
        return hybridAllowed;
    }

    public void setHybridAllowed(Boolean hybridAllowed) {
        this.hybridAllowed = hybridAllowed;
    }

    public Boolean getOfficeAllowed() {
        return officeAllowed;
    }

    public void setOfficeAllowed(Boolean officeAllowed) {
        this.officeAllowed = officeAllowed;
    }
}
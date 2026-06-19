package com.omkar.jobaiassistant.dto;

public class UserProfileRequestDto {

    private String fullName;

    private String phone;

    private String currentRole;

    private String preferredRoles;

    private String currentCity;

    private String preferredCities;

    private Boolean remoteAllowed;

    private Boolean hybridAllowed;

    private Boolean officeAllowed;

    public UserProfileRequestDto() {
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public String getCurrentRole() {
        return currentRole;
    }

    public String getPreferredRoles() {
        return preferredRoles;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public String getPreferredCities() {
        return preferredCities;
    }

    public Boolean getRemoteAllowed() {
        return remoteAllowed;
    }

    public Boolean getHybridAllowed() {
        return hybridAllowed;
    }

    public Boolean getOfficeAllowed() {
        return officeAllowed;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCurrentRole(String currentRole) {
        this.currentRole = currentRole;
    }

    public void setPreferredRoles(String preferredRoles) {
        this.preferredRoles = preferredRoles;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public void setPreferredCities(String preferredCities) {
        this.preferredCities = preferredCities;
    }

    public void setRemoteAllowed(Boolean remoteAllowed) {
        this.remoteAllowed = remoteAllowed;
    }

    public void setHybridAllowed(Boolean hybridAllowed) {
        this.hybridAllowed = hybridAllowed;
    }

    public void setOfficeAllowed(Boolean officeAllowed) {
        this.officeAllowed = officeAllowed;
    }
}
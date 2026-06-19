package com.omkar.jobaiassistant.entity;

import jakarta.persistence.*;
import jakarta.persistence.Column;

@Entity
@Table(name = "user_profiles")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String phone;

    @Column(name = "user_current_role")
    private String currentRole;

    private String preferredRoles;
    // stored as comma-separated for now (we will improve later)

    private String currentCity;

    private String preferredCities;

    private Boolean remoteAllowed;

    private Boolean hybridAllowed;

    private Boolean officeAllowed;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public UserProfile() {
    }

    public Long getId() {
        return id;
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

    public User getUser() {
        return user;
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

    public void setUser(User user) {
        this.user = user;
    }
}
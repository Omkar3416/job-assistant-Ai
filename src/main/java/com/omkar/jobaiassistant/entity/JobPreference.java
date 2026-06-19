package com.omkar.jobaiassistant.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "job_preferences")
public class JobPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String preferredRoles;

    private String preferredLocations;

    private Boolean remoteAllowed;

    private Boolean hybridAllowed;

    private Boolean onsiteAllowed;

    private Integer minimumMatchScore;

    private Integer maxApplicationsPerCompany;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public JobPreference() {
    }

    public Long getId() {
        return id;
    }

    public String getPreferredRoles() {
        return preferredRoles;
    }

    public String getPreferredLocations() {
        return preferredLocations;
    }

    public Boolean getRemoteAllowed() {
        return remoteAllowed;
    }

    public Boolean getHybridAllowed() {
        return hybridAllowed;
    }

    public Boolean getOnsiteAllowed() {
        return onsiteAllowed;
    }

    public Integer getMinimumMatchScore() {
        return minimumMatchScore;
    }

    public Integer getMaxApplicationsPerCompany() {
        return maxApplicationsPerCompany;
    }

    public User getUser() {
        return user;
    }

    public void setPreferredRoles(String preferredRoles) {
        this.preferredRoles = preferredRoles;
    }

    public void setPreferredLocations(String preferredLocations) {
        this.preferredLocations = preferredLocations;
    }

    public void setRemoteAllowed(Boolean remoteAllowed) {
        this.remoteAllowed = remoteAllowed;
    }

    public void setHybridAllowed(Boolean hybridAllowed) {
        this.hybridAllowed = hybridAllowed;
    }

    public void setOnsiteAllowed(Boolean onsiteAllowed) {
        this.onsiteAllowed = onsiteAllowed;
    }

    public void setMinimumMatchScore(Integer minimumMatchScore) {
        this.minimumMatchScore = minimumMatchScore;
    }

    public void setMaxApplicationsPerCompany(Integer maxApplicationsPerCompany) {
        this.maxApplicationsPerCompany = maxApplicationsPerCompany;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
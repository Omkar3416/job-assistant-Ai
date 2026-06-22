package com.omkar.jobaiassistant.dto;

public class JobPreferenceRequestDto {

    private String preferredRoles;

    private String preferredLocations;

    private Boolean remoteAllowed;

    private Boolean hybridAllowed;

    private Boolean onsiteAllowed;

    private Integer minimumMatchScore;

    private Integer maxApplicationsPerCompany;

    public JobPreferenceRequestDto() {
    }

    public String getPreferredRoles() {
        return preferredRoles;
    }

    public void setPreferredRoles(String preferredRoles) {
        this.preferredRoles = preferredRoles;
    }

    public String getPreferredLocations() {
        return preferredLocations;
    }

    public void setPreferredLocations(String preferredLocations) {
        this.preferredLocations = preferredLocations;
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

    public Boolean getOnsiteAllowed() {
        return onsiteAllowed;
    }

    public void setOnsiteAllowed(Boolean onsiteAllowed) {
        this.onsiteAllowed = onsiteAllowed;
    }

    public Integer getMinimumMatchScore() {
        return minimumMatchScore;
    }

    public void setMinimumMatchScore(Integer minimumMatchScore) {
        this.minimumMatchScore = minimumMatchScore;
    }

    public Integer getMaxApplicationsPerCompany() {
        return maxApplicationsPerCompany;
    }

    public void setMaxApplicationsPerCompany(Integer maxApplicationsPerCompany) {
        this.maxApplicationsPerCompany = maxApplicationsPerCompany;
    }
}
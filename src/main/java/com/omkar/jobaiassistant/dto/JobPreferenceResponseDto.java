package com.omkar.jobaiassistant.dto;

public class JobPreferenceResponseDto {

    private Long id;

    private String preferredRoles;

    private String preferredLocations;

    private Boolean remoteAllowed;

    private Boolean hybridAllowed;

    private Boolean onsiteAllowed;

    private Integer minimumMatchScore;

    private Integer maxApplicationsPerCompany;

    private Boolean autoApplyEnabled;

    private String manualSearchKeywords;

    private String message;

    public JobPreferenceResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getAutoApplyEnabled() {
        return autoApplyEnabled;
    }

    public void setMaxApplicationsPerCompany(Integer maxApplicationsPerCompany) {
        this.maxApplicationsPerCompany = maxApplicationsPerCompany;
    }

    public void setAutoApplyEnabled(Boolean autoApplyEnabled) {
        this.autoApplyEnabled = autoApplyEnabled;
    }

    public String getManualSearchKeywords() {
        return manualSearchKeywords;
    }

    public void setManualSearchKeywords(
            String manualSearchKeywords
    ) {
        this.manualSearchKeywords =
                manualSearchKeywords;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
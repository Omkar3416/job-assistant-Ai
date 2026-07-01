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

    private Boolean autoApplyEnabled = false;

    @Column(columnDefinition = "TEXT")
    private String aiSearchKeywords;

    @Column(columnDefinition = "TEXT")
    private String preferredSkills;

    private Integer totalExperienceYears;

    private Double currentCtc;

    private Double expectedCtc;

    private Double minimumSalary;

    private Double maximumSalary;

    private String employmentType;

    private String noticePeriod;

    @Column(columnDefinition = "TEXT")
    private String preferredCompanies;

    @Column(columnDefinition = "TEXT")
    private String excludedCompanies;

    @Column(columnDefinition = "TEXT")
    private String excludedRecruiters;

    @Column(columnDefinition = "TEXT")
    private String manualKeywords;

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

    public Boolean getAutoApplyEnabled() {
        return autoApplyEnabled;
    }

    public String getAiSearchKeywords() {
        return aiSearchKeywords;
    }

    public String getPreferredSkills() {
        return preferredSkills;
    }

    public Integer getTotalExperienceYears() {
        return totalExperienceYears;
    }

    public Double getCurrentCtc() {
        return currentCtc;
    }

    public Double getExpectedCtc() {
        return expectedCtc;
    }

    public Double getMinimumSalary() {
        return minimumSalary;
    }

    public Double getMaximumSalary() {
        return maximumSalary;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public String getNoticePeriod() {
        return noticePeriod;
    }

    public String getPreferredCompanies() {
        return preferredCompanies;
    }

    public String getExcludedCompanies() {
        return excludedCompanies;
    }

    public String getExcludedRecruiters() {
        return excludedRecruiters;
    }

    public String getManualKeywords() {
        return manualKeywords;
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

    public void setAutoApplyEnabled(
            Boolean autoApplyEnabled
    ) {
        this.autoApplyEnabled =
                autoApplyEnabled;
    }

    public void setAiSearchKeywords(
            String aiSearchKeywords
    ) {
        this.aiSearchKeywords = aiSearchKeywords;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPreferredSkills(String preferredSkills) {
        this.preferredSkills = preferredSkills;
    }

    public void setTotalExperienceYears(Integer totalExperienceYears) {
        this.totalExperienceYears = totalExperienceYears;
    }

    public void setCurrentCtc(Double currentCtc) {
        this.currentCtc = currentCtc;
    }

    public void setExpectedCtc(Double expectedCtc) {
        this.expectedCtc = expectedCtc;
    }

    public void setMinimumSalary(Double minimumSalary) {
        this.minimumSalary = minimumSalary;
    }

    public void setMaximumSalary(Double maximumSalary) {
        this.maximumSalary = maximumSalary;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public void setNoticePeriod(String noticePeriod) {
        this.noticePeriod = noticePeriod;
    }

    public void setPreferredCompanies(String preferredCompanies) {
        this.preferredCompanies = preferredCompanies;
    }

    public void setExcludedCompanies(String excludedCompanies) {
        this.excludedCompanies = excludedCompanies;
    }

    public void setExcludedRecruiters(String excludedRecruiters) {
        this.excludedRecruiters = excludedRecruiters;
    }

    public void setManualKeywords(String manualKeywords) {
        this.manualKeywords = manualKeywords;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
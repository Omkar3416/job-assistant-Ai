package com.omkar.jobaiassistant.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String companyName;

    private String location;

    private String employmentType;

    private String experienceLevel;

    private String salary;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private String jobUrl;

    private String sourcePortal;

    private Boolean easyApply;

    private Boolean externalApply;

    private String externalJobId;

    private Boolean remoteAllowed;

    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Job() {
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        if (this.active == null) {
            this.active = true;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getLocation() {
        return location;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public String getSalary() {
        return salary;
    }

    public String getDescription() {
        return description;
    }

    public String getJobUrl() {
        return jobUrl;
    }

    public String getSourcePortal() {
        return sourcePortal;
    }

    public Boolean getEasyApply() {
        return easyApply;
    }

    public Boolean getExternalApply() {
        return externalApply;
    }

    public String getExternalJobId() {
        return externalJobId;
    }

    public Boolean getRemoteAllowed() {
        return remoteAllowed;
    }

    public Boolean getActive() {
        return active;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setJobUrl(String jobUrl) {
        this.jobUrl = jobUrl;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSourcePortal(String sourcePortal) {
        this.sourcePortal = sourcePortal;
    }

    public void setEasyApply(Boolean easyApply) {
        this.easyApply = easyApply;
    }

    public void setExternalApply(Boolean externalApply) {
        this.externalApply = externalApply;
    }

    public void setExternalJobId(String externalJobId) {
        this.externalJobId = externalJobId;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setRemoteAllowed(Boolean remoteAllowed) {
        this.remoteAllowed = remoteAllowed;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
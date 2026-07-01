package com.omkar.jobaiassistant.dto;

public class NaukriJobDto {

    private String title;

    private String company;

    private String url;

    private String location;

    private String experience;

    private String salary;

    private String description;

    private Boolean easyApply;

    private Boolean externalApply;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public String getLocation() {
        return location;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getEasyApply() {
        return easyApply;
    }

    public void setEasyApply(Boolean easyApply) {
        this.easyApply = easyApply;
    }

    public Boolean getExternalApply() {
        return externalApply;
    }

    public void setExternalApply(Boolean externalApply) {
        this.externalApply = externalApply;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
package com.omkar.jobaiassistant.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "skills")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String skillName;

    private Double experienceFrom;

    private Double experienceTo;

    @Enumerated(EnumType.STRING)
    private SkillLevel level;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Skill() {
    }

    public Long getId() {
        return id;
    }

    public String getSkillName() {
        return skillName;
    }

    public Double getExperienceFrom() {
        return experienceFrom;
    }

    public Double getExperienceTo() {
        return experienceTo;
    }

    public SkillLevel getLevel() {
        return level;
    }

    public User getUser() {
        return user;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public void setExperienceFrom(Double experienceFrom) {
        this.experienceFrom = experienceFrom;
    }

    public void setExperienceTo(Double experienceTo) {
        this.experienceTo = experienceTo;
    }

    public void setLevel(SkillLevel level) {
        this.level = level;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
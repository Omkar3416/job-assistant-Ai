package com.omkar.jobaiassistant.dto;

import java.util.List;

public class MatchScoreResponseDto {

    private Integer score;

    private String analysis;

    private Boolean skillMatched;

    private java.util.List<String> matchedSkills;

    private java.util.List<String> missingSkills;

    public Boolean getSkillMatched() {
        return skillMatched;
    }

    public void setSkillMatched(Boolean skillMatched) {
        this.skillMatched = skillMatched;
    }

    public List<String> getMatchedSkills() {
        return matchedSkills;
    }

    public void setMatchedSkills(List<String> matchedSkills) {
        this.matchedSkills = matchedSkills;
    }

    public List<String> getMissingSkills() {
        return missingSkills;
    }

    public void setMissingSkills(List<String> missingSkills) {
        this.missingSkills = missingSkills;
    }

    public MatchScoreResponseDto() {
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }
}
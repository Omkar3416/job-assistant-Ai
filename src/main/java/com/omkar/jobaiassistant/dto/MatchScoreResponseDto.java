package com.omkar.jobaiassistant.dto;

public class MatchScoreResponseDto {

    private Integer score;

    private String analysis;

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
package com.omkar.jobaiassistant.dto;

public class ApplicationAnswerResponseDto {

    private String answer;

    public ApplicationAnswerResponseDto() {
    }

    public ApplicationAnswerResponseDto(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
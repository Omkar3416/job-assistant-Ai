package com.omkar.jobaiassistant.service;

public interface ApplicationAnswerService {

    String generateAnswer(
            String email,
            String question
    );

}
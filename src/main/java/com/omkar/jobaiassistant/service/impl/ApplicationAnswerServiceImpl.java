package com.omkar.jobaiassistant.service.impl;

import com.omkar.jobaiassistant.entity.Resume;
import com.omkar.jobaiassistant.entity.User;
import com.omkar.jobaiassistant.entity.UserProfile;
import com.omkar.jobaiassistant.repository.ResumeRepository;
import com.omkar.jobaiassistant.repository.UserProfileRepository;
import com.omkar.jobaiassistant.repository.UserRepository;
import com.omkar.jobaiassistant.service.ApplicationAnswerService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ApplicationAnswerServiceImpl
        implements ApplicationAnswerService {

    private final UserRepository userRepository;

    private final ResumeRepository resumeRepository;

    private final UserProfileRepository userProfileRepository;

    private final ChatClient chatClient;

    public ApplicationAnswerServiceImpl(
            UserRepository userRepository,
            ResumeRepository resumeRepository,
            UserProfileRepository userProfileRepository,
            ChatClient.Builder builder
    ) {

        this.userRepository = userRepository;
        this.resumeRepository = resumeRepository;
        this.userProfileRepository = userProfileRepository;
        this.chatClient = builder.build();

    }

    @Override
    public String generateAnswer(
            String email,
            String question
    ) {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        Resume resume =
                resumeRepository
                        .findByUserId(user.getId())
                        .orElse(null);

        UserProfile profile =
                userProfileRepository
                        .findByUserId(user.getId())
                        .orElse(null);

        String resumeText =
                resume != null
                        ? resume.getExtractedText()
                        : "";

        String aiSummary =
                resume != null
                        ? resume.getAiSummary()
                        : "";

        String currentRole =
                profile != null
                        ? profile.getCurrentRole()
                        : "";

        String preferredRoles =
                profile != null
                        ? profile.getPreferredRoles()
                        : "";

        String currentCity =
                profile != null
                        ? profile.getCurrentCity()
                        : "";

        String preferredCities =
                profile != null
                        ? profile.getPreferredCities()
                        : "";

        String prompt =
                """
                You are answering a job application question.

                Answer ONLY using the user's information.

                Rules:

                - Never invent information.
                - Never exaggerate.
                - Keep answer under 20 words.
                - If yes/no question, answer only Yes or No.
                - If experience question, return only the number or short answer.
                - If date question, answer with the best available date.
                - If location question, use the user's preferred/current location.
                - If answer is unavailable, return ONLY:
                  Not Available

                User Current Role:
                %s

                Preferred Roles:
                %s

                Current City:
                %s

                Preferred Cities:
                %s

                AI Resume Summary:
                %s

                Resume:
                %s

                Question:
                %s
                """
                        .formatted(
                                currentRole,
                                preferredRoles,
                                currentCity,
                                preferredCities,
                                aiSummary,
                                resumeText,
                                question
                        );

        String answer =
                chatClient
                        .prompt(prompt)
                        .call()
                        .content();

        if (answer == null || answer.isBlank()) {

            return "Not Available";

        }

        return answer.trim();

    }

}
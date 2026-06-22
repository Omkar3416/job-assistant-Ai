package com.omkar.jobaiassistant.service.impl;

import com.omkar.jobaiassistant.service.AiResumeService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;


@Service
public class AiResumeServiceImpl
        implements AiResumeService {

    private final ChatClient chatClient;

    public AiResumeServiceImpl(
            ChatClient.Builder builder
    ) {
        this.chatClient =
                builder.build();
    }

    @Override
    public String generateResumeSummary(
            String resumeText
    ) {

        String prompt =
                """
                Analyze this resume.
        
                Return ONLY:
        
                PROFESSIONAL SUMMARY
        
                TOP SKILLS
        
                YEARS OF EXPERIENCE
        
                RECOMMENDED JOB ROLES
        
                STRENGTHS
        
                MISSING SKILLS
        
                Use bullet points.
        
                Keep response under 300 words.
        
                Resume:
        
                %s
                """
                        .formatted(resumeText);

        return chatClient
                .prompt(prompt)
                .call()
                .content();
    }
}
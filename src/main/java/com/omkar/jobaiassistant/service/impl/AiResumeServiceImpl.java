package com.omkar.jobaiassistant.service.impl;

import com.omkar.jobaiassistant.service.AiResumeService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import com.omkar.jobaiassistant.dto.MatchScoreResponseDto;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

    @Override
    public MatchScoreResponseDto calculateMatchScore(
            String resumeText,
            String jobDescription
    ) {

        String prompt =
                """
                You are an ATS Resume Analyzer.
        
                IMPORTANT RULES:
        
                1. Carefully read the resume.
                2. Do NOT mark a skill as missing if it already exists anywhere in the resume.
                3. Check skills case-insensitively.
                4. If a skill exists in the resume, put it only under Matched Skills.
                5. Missing Skills must contain ONLY skills that appear in the job description but do not appear anywhere in the resume.
                6. Do not guess.
                7. Do not invent skills.
        
                Return EXACTLY:
        
                MATCH SCORE: XX
        
                ## Matched Skills
                - skill
        
                ## Missing Skills
                - skill
        
                ## Strengths
                - point
        
                ## Improvements
                - point
        
                Resume:
                %s
        
                Job Description:
                %s
                """
                        .formatted(
                                resumeText,
                                jobDescription
                        );

        String aiResponse =
                chatClient
                        .prompt(prompt)
                        .call()
                        .content();

        MatchScoreResponseDto response =
                new MatchScoreResponseDto();

        Pattern pattern =
                Pattern.compile(
                        "MATCH SCORE:\\s*(\\d+)"
                );

        Matcher matcher =
                pattern.matcher(aiResponse);

        if (matcher.find()) {

            response.setScore(
                    Integer.parseInt(
                            matcher.group(1)
                    )
            );

        } else {

            response.setScore(0);
        }

        response.setAnalysis(
                aiResponse
        );

        return response;
    }
}
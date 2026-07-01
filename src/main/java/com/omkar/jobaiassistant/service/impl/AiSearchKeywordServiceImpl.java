package com.omkar.jobaiassistant.service.impl;

import com.omkar.jobaiassistant.entity.JobPreference;
import com.omkar.jobaiassistant.entity.Resume;
import com.omkar.jobaiassistant.entity.Skill;
import com.omkar.jobaiassistant.repository.JobPreferenceRepository;
import com.omkar.jobaiassistant.repository.ResumeRepository;
import com.omkar.jobaiassistant.repository.SkillRepository;
import com.omkar.jobaiassistant.service.AiResumeService;
import com.omkar.jobaiassistant.service.AiSearchKeywordService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AiSearchKeywordServiceImpl
        implements AiSearchKeywordService {

    private final ResumeRepository resumeRepository;

    private final SkillRepository skillRepository;

    private final JobPreferenceRepository jobPreferenceRepository;

    private final AiResumeService aiResumeService;

    public AiSearchKeywordServiceImpl(
            ResumeRepository resumeRepository,
            SkillRepository skillRepository,
            JobPreferenceRepository jobPreferenceRepository,
            AiResumeService aiResumeService
    ) {

        this.resumeRepository =
                resumeRepository;

        this.skillRepository =
                skillRepository;

        this.jobPreferenceRepository =
                jobPreferenceRepository;

        this.aiResumeService =
                aiResumeService;
    }

    @Override
    public void refreshKeywords(
            Long userId
    ) {

        Resume resume =
                resumeRepository
                        .findByUserId(userId)
                        .orElse(null);

        JobPreference preference =
                jobPreferenceRepository
                        .findByUserId(userId)
                        .orElse(null);

        if (
                resume == null ||
                        preference == null
        ) {
            return;
        }

        List<Skill> skills =
                skillRepository.findByUserId(userId);

        String skillText =
                skills.stream()
                        .map(
                                Skill::getSkillName
                        )
                        .collect(
                                Collectors.joining(", ")
                        );

        String keywords =
                aiResumeService.generateSearchKeywords(
                        resume.getExtractedText(),
                        skillText,
                        preference.getPreferredRoles(),
                        preference.getPreferredLocations()
                );

        preference.setAiSearchKeywords(
                keywords
        );

        jobPreferenceRepository.save(
                preference
        );
    }
}
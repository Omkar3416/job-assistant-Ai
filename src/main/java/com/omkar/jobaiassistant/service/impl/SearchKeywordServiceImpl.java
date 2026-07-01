package com.omkar.jobaiassistant.service.impl;

import com.omkar.jobaiassistant.entity.Skill;
import com.omkar.jobaiassistant.entity.User;
import com.omkar.jobaiassistant.repository.SkillRepository;
import com.omkar.jobaiassistant.repository.JobPreferenceRepository;
import com.omkar.jobaiassistant.entity.JobPreference;
import com.omkar.jobaiassistant.service.SearchKeywordService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class SearchKeywordServiceImpl
        implements SearchKeywordService {

    private final SkillRepository skillRepository;

    private final JobPreferenceRepository
            jobPreferenceRepository;

    public SearchKeywordServiceImpl(
            SkillRepository skillRepository,
            JobPreferenceRepository jobPreferenceRepository
    ) {
        this.skillRepository = skillRepository;
        this.jobPreferenceRepository =
                jobPreferenceRepository;
    }
    @Override
    public List<String> buildSearchKeywords(
            User user
    ) {

        List<Skill> skills =
                skillRepository.findByUserId(
                        user.getId()
                );

        JobPreference preference =
                jobPreferenceRepository
                        .findByUserId(
                                user.getId()
                        )
                        .orElse(null);

        Set<String> roles =
                new LinkedHashSet<>();

        Set<String> keywords =
                new LinkedHashSet<>();

        /*
         * Preferred Roles
         */
        if (
                preference != null
                        &&
                        preference.getPreferredRoles() != null
                        &&
                        !preference.getPreferredRoles().isBlank()
        ) {

            for (
                    String role :
                    preference.getPreferredRoles().split(",")
            ) {

                if (!role.isBlank()) {

                    roles.add(role.trim());

                }

            }

        }

        /*
         * Skills
         */
        for (Skill skill : skills) {

            if (
                    skill.getSkillName() != null
                            &&
                            !skill.getSkillName().isBlank()
            ) {

                keywords.add(
                        skill.getSkillName().trim()
                );

            }

        }

        /*
         * AI Keywords
         */
        if (
                preference != null
                        &&
                        preference.getAiSearchKeywords() != null
                        &&
                        !preference.getAiSearchKeywords().isBlank()
        ) {

            for (
                    String keyword :
                    preference.getAiSearchKeywords().split(",")
            ) {

                if (!keyword.isBlank()) {

                    keywords.add(
                            keyword.trim()
                    );

                }

            }

        }

        /*
         * Manual Keywords
         */
        if (
                preference != null
                        &&
                        preference.getManualKeywords() != null
                        &&
                        !preference.getManualKeywords().isBlank()
        ) {

            for (
                    String keyword :
                    preference.getManualKeywords().split(",")
            ) {

                if (!keyword.isBlank()) {

                    keywords.add(
                            keyword.trim()
                    );

                }

            }

        }

        if (roles.isEmpty()) {

            roles.add("Java Developer");

        }

        if (keywords.isEmpty()) {

            keywords.add("Java");

        }

        List<String> searchKeywords =
                new ArrayList<>();

        /*
         * Add preferred roles
         */
        searchKeywords.addAll(roles);

        /*
         * Add skills separately
         */
        searchKeywords.addAll(keywords);

        /*
         * Remove duplicates while preserving order
         */
        return new ArrayList<>(
                new LinkedHashSet<>(searchKeywords)
        );

    }
}
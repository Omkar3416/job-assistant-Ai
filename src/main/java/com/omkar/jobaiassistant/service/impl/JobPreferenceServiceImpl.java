package com.omkar.jobaiassistant.service.impl;

import com.omkar.jobaiassistant.dto.JobPreferenceRequestDto;
import com.omkar.jobaiassistant.dto.JobPreferenceResponseDto;
import com.omkar.jobaiassistant.entity.JobPreference;
import com.omkar.jobaiassistant.entity.User;
import com.omkar.jobaiassistant.repository.JobPreferenceRepository;
import com.omkar.jobaiassistant.repository.UserRepository;
import com.omkar.jobaiassistant.service.JobPreferenceService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class JobPreferenceServiceImpl
        implements JobPreferenceService {

    private final JobPreferenceRepository jobPreferenceRepository;

    private final UserRepository userRepository;

    public JobPreferenceServiceImpl(
            JobPreferenceRepository jobPreferenceRepository,
            UserRepository userRepository
    ) {
        this.jobPreferenceRepository = jobPreferenceRepository;
        this.userRepository = userRepository;
    }

    @Override
    public JobPreferenceResponseDto savePreference(
            JobPreferenceRequestDto request
    ) {

        Authentication auth =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email =
                auth.getName();

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        JobPreference existingPreference =
                jobPreferenceRepository
                        .findByUserId(user.getId())
                        .orElse(null);

        boolean isUpdate =
                existingPreference != null;

        JobPreference preference =
                isUpdate
                        ? existingPreference
                        : new JobPreference();

        preference.setPreferredRoles(
                request.getPreferredRoles()
        );

        preference.setPreferredLocations(
                request.getPreferredLocations()
        );

        preference.setRemoteAllowed(
                request.getRemoteAllowed()
        );

        preference.setHybridAllowed(
                request.getHybridAllowed()
        );

        preference.setOnsiteAllowed(
                request.getOnsiteAllowed()
        );

        preference.setMinimumMatchScore(
                request.getMinimumMatchScore()
        );

        preference.setMaxApplicationsPerCompany(
                request.getMaxApplicationsPerCompany()
        );

        preference.setUser(user);

        JobPreference saved =
                jobPreferenceRepository.save(preference);

        JobPreferenceResponseDto response =
                new JobPreferenceResponseDto();

        response.setId(saved.getId());

        response.setPreferredRoles(
                saved.getPreferredRoles()
        );

        response.setPreferredLocations(
                saved.getPreferredLocations()
        );

        response.setRemoteAllowed(
                saved.getRemoteAllowed()
        );

        response.setHybridAllowed(
                saved.getHybridAllowed()
        );

        response.setOnsiteAllowed(
                saved.getOnsiteAllowed()
        );

        response.setMinimumMatchScore(
                saved.getMinimumMatchScore()
        );

        response.setMaxApplicationsPerCompany(
                saved.getMaxApplicationsPerCompany()
        );

        response.setMessage(
                isUpdate
                        ? "Job preferences updated successfully"
                        : "Job preferences saved successfully"
        );

        return response;
    }

    @Override
    public JobPreferenceResponseDto getPreference(
            String email
    ) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        JobPreference preference =
                jobPreferenceRepository
                        .findByUserId(user.getId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Job preference not found"
                                )
                        );

        JobPreferenceResponseDto response =
                new JobPreferenceResponseDto();

        response.setId(
                preference.getId()
        );

        response.setPreferredRoles(
                preference.getPreferredRoles()
        );

        response.setPreferredLocations(
                preference.getPreferredLocations()
        );

        response.setRemoteAllowed(
                preference.getRemoteAllowed()
        );

        response.setHybridAllowed(
                preference.getHybridAllowed()
        );

        response.setOnsiteAllowed(
                preference.getOnsiteAllowed()
        );

        response.setMinimumMatchScore(
                preference.getMinimumMatchScore()
        );

        response.setMaxApplicationsPerCompany(
                preference.getMaxApplicationsPerCompany()
        );

        response.setMessage(
                "Job preferences fetched successfully"
        );

        return response;
    }
}
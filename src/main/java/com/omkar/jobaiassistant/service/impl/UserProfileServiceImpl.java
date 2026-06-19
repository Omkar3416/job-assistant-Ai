package com.omkar.jobaiassistant.service.impl;

import com.omkar.jobaiassistant.dto.UserProfileRequestDto;
import com.omkar.jobaiassistant.dto.UserProfileResponseDto;
import com.omkar.jobaiassistant.entity.User;
import com.omkar.jobaiassistant.entity.UserProfile;
import com.omkar.jobaiassistant.repository.UserProfileRepository;
import com.omkar.jobaiassistant.repository.UserRepository;
import com.omkar.jobaiassistant.service.UserProfileService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    public UserProfileServiceImpl(
            UserProfileRepository userProfileRepository,
            UserRepository userRepository
    ) {
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserProfileResponseDto createProfile(UserProfileRequestDto request) {

        // ✅ FIXED JWT USER FETCH
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile profile = new UserProfile();

        profile.setFullName(request.getFullName());
        profile.setPhone(request.getPhone());
        profile.setCurrentRole(request.getCurrentRole());
        profile.setPreferredRoles(request.getPreferredRoles());
        profile.setCurrentCity(request.getCurrentCity());
        profile.setPreferredCities(request.getPreferredCities());
        profile.setRemoteAllowed(request.getRemoteAllowed());
        profile.setHybridAllowed(request.getHybridAllowed());
        profile.setOfficeAllowed(request.getOfficeAllowed());

        profile.setUser(user);

        UserProfile savedProfile = userProfileRepository.save(profile);

        UserProfileResponseDto response = new UserProfileResponseDto();
        response.setId(savedProfile.getId());
        response.setFullName(savedProfile.getFullName());
        response.setMessage("Profile saved successfully");

        return response;
    }

    @Override
    public UserProfileResponseDto getProfile(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile profile = userProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        UserProfileResponseDto response = new UserProfileResponseDto();
        response.setId(profile.getId());
        response.setFullName(profile.getFullName());
        response.setMessage("Profile fetched successfully");

        return response;
    }
}
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

        UserProfile existingProfile =
                userProfileRepository
                        .findByUserId(user.getId())
                        .orElse(null);

        boolean isUpdate =
                existingProfile != null;

        System.out.println("LOGGED USER EMAIL = " + email);
        System.out.println("LOGGED USER ID = " + user.getId());

        System.out.println(
                "PROFILE FOUND = " +
                        userProfileRepository
                                .findByUserId(user.getId())
                                .isPresent()
        );

        UserProfile profile =
                isUpdate
                        ? existingProfile
                        : new UserProfile();

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

        response.setPhone(savedProfile.getPhone());

        response.setCurrentRole(savedProfile.getCurrentRole());

        response.setPreferredRoles(savedProfile.getPreferredRoles());

        response.setCurrentCity(savedProfile.getCurrentCity());

        response.setPreferredCities(savedProfile.getPreferredCities());

        response.setRemoteAllowed(savedProfile.getRemoteAllowed());

        response.setHybridAllowed(savedProfile.getHybridAllowed());

        response.setOfficeAllowed(savedProfile.getOfficeAllowed());
        response.setMessage(
                isUpdate
                        ? "Profile updated successfully"
                        : "Profile created successfully"
        );

        return response;
    }



    @Override
    public UserProfileResponseDto getProfile(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile profile = userProfileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        System.out.println(
                "USER ID = " + user.getId()
        );

        System.out.println(
                "PROFILE EXISTS = " +
                        userProfileRepository
                                .findByUserId(user.getId())
                                .isPresent()
        );

        UserProfileResponseDto response = new UserProfileResponseDto();
        response.setId(profile.getId());

        response.setFullName(profile.getFullName());

        response.setPhone(profile.getPhone());

        response.setCurrentRole(profile.getCurrentRole());

        response.setPreferredRoles(profile.getPreferredRoles());

        response.setCurrentCity(profile.getCurrentCity());

        response.setPreferredCities(profile.getPreferredCities());

        response.setRemoteAllowed(profile.getRemoteAllowed());

        response.setHybridAllowed(profile.getHybridAllowed());

        response.setOfficeAllowed(profile.getOfficeAllowed());

        response.setMessage("Profile fetched successfully");

        return response;
    }
}
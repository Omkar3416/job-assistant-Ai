package com.omkar.jobaiassistant.controller;

import com.omkar.jobaiassistant.dto.UserProfileRequestDto;
import com.omkar.jobaiassistant.dto.UserProfileResponseDto;
import com.omkar.jobaiassistant.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PostMapping
    public ResponseEntity<UserProfileResponseDto> createProfile(
            @RequestBody UserProfileRequestDto request
    ) {

        UserProfileResponseDto response =
                userProfileService.createProfile(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<UserProfileResponseDto> getProfile(
            Authentication authentication
    ) {

        String email = authentication.getName();

        UserProfileResponseDto response =
                userProfileService.getProfile(email);

        return ResponseEntity.ok(response);
    }
}
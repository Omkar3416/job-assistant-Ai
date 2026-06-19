package com.omkar.jobaiassistant.service.impl;

import com.omkar.jobaiassistant.dto.AuthResponseDto;
import com.omkar.jobaiassistant.dto.LoginRequestDto;
import com.omkar.jobaiassistant.dto.RegisterRequestDto;
import com.omkar.jobaiassistant.dto.RegisterResponseDto;
import com.omkar.jobaiassistant.entity.Role;
import com.omkar.jobaiassistant.entity.RoleName;
import com.omkar.jobaiassistant.entity.User;
import com.omkar.jobaiassistant.entity.RefreshToken;
import com.omkar.jobaiassistant.repository.RoleRepository;
import com.omkar.jobaiassistant.repository.UserRepository;
import com.omkar.jobaiassistant.security.JwtService;
import com.omkar.jobaiassistant.security.RefreshTokenService;
import com.omkar.jobaiassistant.security.TokenBlacklistService;
import com.omkar.jobaiassistant.security.UserSessionService;
import com.omkar.jobaiassistant.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.UUID;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final TokenBlacklistService blacklistService;
    private final UserSessionService userSessionService;

    String sessionId = UUID.randomUUID().toString();
    String device = "UNKNOWN";
    String ip = "UNKNOWN";

    public AuthServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            RefreshTokenService refreshTokenService,
            TokenBlacklistService blacklistService,
            UserSessionService userSessionService
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.blacklistService = blacklistService;
        this.userSessionService = userSessionService;
    }

    @Override
    public RegisterResponseDto register(RegisterRequestDto request) {

        boolean exists = userRepository.existsByEmail(request.getEmail());

        if (exists) {
            throw new RuntimeException("User already exists. Please login instead.");
        }

        Role userRole = roleRepository.findByName(RoleName.USER)
                .orElseThrow(() -> new RuntimeException("USER role missing in database"));

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(userRole);

        User savedUser = userRepository.save(user);

        RegisterResponseDto response = new RegisterResponseDto();
        response.setId(savedUser.getId());
        response.setEmail(savedUser.getEmail());
        response.setRole(savedUser.getRole().getName().name());
        response.setMessage("Registration successful");

        return response;
    }

    @Override
    public AuthResponseDto login(LoginRequestDto request) {

        System.out.println("LOGIN ATTEMPT: " + request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        System.out.println("User found: " + user.getEmail());

        boolean passwordMatches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        System.out.println("Password match: " + passwordMatches);

        if (!passwordMatches) {
            throw new RuntimeException("Invalid email or password");
        }

        String accessToken = jwtService.generateToken(
                user.getEmail(),
                user.getRole().getName().name(),
                user.getId()
        );

        refreshTokenService.deleteByUser(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        userSessionService.createSession(
                user,
                refreshToken.getToken(),
                device,
                ip
        );

        AuthResponseDto response = new AuthResponseDto();
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().getName().name());
        response.setToken(accessToken);
        response.setRefreshToken(refreshToken.getToken());

        return response;
    }

    @Override
    public AuthResponseDto refreshToken(String refreshToken) {

        RefreshToken token = refreshTokenService.validate(refreshToken);

        User user = token.getUser();

        userSessionService.deactivateSession(refreshToken);

        String newAccessToken = jwtService.generateToken(
                user.getEmail(),
                user.getRole().getName().name(),
                user.getId()
        );

        AuthResponseDto response = new AuthResponseDto();
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().getName().name());
        response.setToken(newAccessToken);
        response.setRefreshToken(refreshToken);

        return response;
    }
    @Transactional
    @Override
    public void logout(
            String refreshToken,
            String accessToken
    ) {

        RefreshToken token =
                refreshTokenService.validate(refreshToken);

        blacklistService.blacklist(
                accessToken,
                jwtService.getExpirationMillis()
        );

        userSessionService.deactivateSession(refreshToken);

        refreshTokenService.delete(token);
    }
}
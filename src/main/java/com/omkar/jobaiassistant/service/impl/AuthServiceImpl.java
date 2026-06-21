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
import com.omkar.jobaiassistant.security.GoogleTokenVerifierService;
import com.omkar.jobaiassistant.dto.CurrentUserDto;


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
    private final GoogleTokenVerifierService googleTokenVerifierService;

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
            UserSessionService userSessionService,
            GoogleTokenVerifierService googleTokenVerifierService
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.blacklistService = blacklistService;
        this.userSessionService = userSessionService;
        this.googleTokenVerifierService = googleTokenVerifierService;
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

        user.setProvider("LOCAL");
        user.setProviderId(null);

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

        int validityDays =
                request.isRememberMe()
                        ? 30
                        : 7;

        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(
                        user,
                        validityDays
                );

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
        response.setRefreshToken(
                refreshToken.getToken()
        );

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
    @Override
    public AuthResponseDto googleLogin(String idToken) {

        var payload =
                googleTokenVerifierService.verify(idToken);

        String email = payload.getEmail();

        String googleUserId =
                payload.getSubject();

        User user = userRepository
                .findByEmail(email)
                .orElse(null);

        if (user == null) {

            Role userRole = roleRepository
                    .findByName(RoleName.USER)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "USER role missing in database"
                            )
                    );

            user = new User();

            user.setEmail(email);

            user.setPassword(
                    passwordEncoder.encode(
                            UUID.randomUUID().toString()
                    )
            );

            user.setRole(userRole);

            user.setProvider("GOOGLE");

            user.setProviderId(
                    googleUserId
            );

            user = userRepository.save(user);
        }

        String accessToken =
                jwtService.generateToken(
                        user.getEmail(),
                        user.getRole().getName().name(),
                        user.getId()
                );

        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(
                        user,
                        30
                );

        userSessionService.createSession(
                user,
                refreshToken.getToken(),
                device,
                ip
        );

        AuthResponseDto response =
                new AuthResponseDto();

        response.setEmail(
                user.getEmail()
        );

        response.setRole(
                user.getRole()
                        .getName()
                        .name()
        );

        response.setToken(
                accessToken
        );

        response.setRefreshToken(
                refreshToken.getToken()
        );

        return response;
    }

    @Override
    public CurrentUserDto getCurrentUser(String email) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        CurrentUserDto dto =
                new CurrentUserDto();

        dto.setId(user.getId());

        dto.setEmail(user.getEmail());

        dto.setRole(
                user.getRole()
                        .getName()
                        .name()
        );

        dto.setProvider(
                user.getProvider()
        );

        return dto;
    }

}
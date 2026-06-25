package com.omkar.jobaiassistant.service.impl;

import com.omkar.jobaiassistant.dto.NaukriCredentialRequestDto;
import com.omkar.jobaiassistant.dto.NaukriCredentialResponseDto;
import com.omkar.jobaiassistant.entity.NaukriCredential;
import com.omkar.jobaiassistant.entity.User;
import com.omkar.jobaiassistant.repository.NaukriCredentialRepository;
import com.omkar.jobaiassistant.repository.UserRepository;
import com.omkar.jobaiassistant.security.CredentialEncryptionService;
import com.omkar.jobaiassistant.service.NaukriCredentialService;
import org.springframework.stereotype.Service;

@Service
public class NaukriCredentialServiceImpl
        implements NaukriCredentialService {

    private final NaukriCredentialRepository
            naukriCredentialRepository;

    private final UserRepository
            userRepository;

    private final CredentialEncryptionService
            credentialEncryptionService;

    public NaukriCredentialServiceImpl(
            NaukriCredentialRepository naukriCredentialRepository,
            UserRepository userRepository,
            CredentialEncryptionService credentialEncryptionService
    ) {
        this.naukriCredentialRepository =
                naukriCredentialRepository;

        this.userRepository =
                userRepository;

        this.credentialEncryptionService =
                credentialEncryptionService;
    }

    @Override
    public NaukriCredentialResponseDto saveCredentials(
            String email,
            NaukriCredentialRequestDto request
    ) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        NaukriCredential credential =
                naukriCredentialRepository
                        .findByUserId(user.getId())
                        .orElse(
                                new NaukriCredential()
                        );

        credential.setUser(user);

        credential.setNaukriEmail(
                request.getNaukriEmail()
        );
        if (
                request.getNaukriPassword() == null ||
                        request.getNaukriPassword().isBlank()
        ) {
            throw new RuntimeException(
                    "Naukri password is required"
            );
        }

        credential.setEncryptedPassword(
                credentialEncryptionService.encrypt(
                        request.getNaukriPassword()
                )
        );

        naukriCredentialRepository.save(
                credential
        );

        NaukriCredentialResponseDto response =
                new NaukriCredentialResponseDto();

        response.setNaukriEmail(
                credential.getNaukriEmail()
        );

        response.setConfigured(true);

        return response;
    }

    @Override
    public NaukriCredentialResponseDto getCredentials(
            String email
    ) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        NaukriCredentialResponseDto response =
                new NaukriCredentialResponseDto();

        naukriCredentialRepository
                .findByUserId(user.getId())
                .ifPresent(credential -> {

                    response.setNaukriEmail(
                            credential.getNaukriEmail()
                    );

                    response.setConfigured(true);
                });

        return response;
    }

    @Override
    public String getDecryptedPassword(
            String email
    ) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        NaukriCredential credential =
                naukriCredentialRepository
                        .findByUserId(user.getId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Credentials not found"
                                )
                        );

        return credentialEncryptionService.decrypt(
                credential.getEncryptedPassword()
        );
    }
}
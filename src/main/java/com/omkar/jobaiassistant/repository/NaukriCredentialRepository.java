package com.omkar.jobaiassistant.repository;

import com.omkar.jobaiassistant.entity.NaukriCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NaukriCredentialRepository
        extends JpaRepository<NaukriCredential, Long> {

    Optional<NaukriCredential>
    findByUserId(Long userId);
}
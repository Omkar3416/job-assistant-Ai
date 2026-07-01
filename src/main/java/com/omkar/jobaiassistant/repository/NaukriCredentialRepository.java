package com.omkar.jobaiassistant.repository;

import com.omkar.jobaiassistant.entity.NaukriCredential;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;
import java.util.List;

public interface NaukriCredentialRepository
        extends JpaRepository<NaukriCredential, Long> {

    Optional<NaukriCredential>
    findByUserId(Long userId);

    List<NaukriCredential> findAll();
}
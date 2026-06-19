package com.omkar.jobaiassistant.repository;

import com.omkar.jobaiassistant.entity.JobPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobPreferenceRepository extends JpaRepository<JobPreference, Long> {

    Optional<JobPreference> findByUserId(Long userId);
}
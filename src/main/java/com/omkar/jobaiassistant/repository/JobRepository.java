package com.omkar.jobaiassistant.repository;

import com.omkar.jobaiassistant.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository
        extends JpaRepository<Job, Long> {

    List<Job> findByActiveTrue();

    List<Job> findByTitleContainingIgnoreCaseAndActiveTrue(
            String keyword
    );

    List<Job> findByCompanyNameContainingIgnoreCaseAndActiveTrue(
            String keyword
    );

    List<Job> findByLocationContainingIgnoreCaseAndActiveTrue(
            String keyword
    );

    boolean existsByUserIdAndJobUrl(
            Long userId,
            String jobUrl
    );

    List<Job> findByUserIdAndActiveTrue(
            Long userId
    );

    List<Job> findByUserIdAndActiveTrueOrderByCreatedAtDesc(
            Long userId
    );
}
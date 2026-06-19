package com.omkar.jobaiassistant.repository;

import com.omkar.jobaiassistant.entity.ApplicationStatus;
import com.omkar.jobaiassistant.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    List<JobApplication> findByUserId(Long userId);

    List<JobApplication> findByStatus(ApplicationStatus status);

    boolean existsByUserIdAndCompanyNameAndJobTitle(
            Long userId,
            String companyName,
            String jobTitle
    );
}
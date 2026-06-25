package com.omkar.jobaiassistant.service.impl;

import com.omkar.jobaiassistant.dto.JobResponseDto;
import com.omkar.jobaiassistant.entity.Job;
import com.omkar.jobaiassistant.entity.JobPreference;
import com.omkar.jobaiassistant.entity.User;
import com.omkar.jobaiassistant.entity.UserProfile;
import com.omkar.jobaiassistant.repository.JobPreferenceRepository;
import com.omkar.jobaiassistant.repository.JobRepository;
import com.omkar.jobaiassistant.repository.UserProfileRepository;
import com.omkar.jobaiassistant.repository.UserRepository;
import com.omkar.jobaiassistant.service.JobService;
import org.springframework.stereotype.Service;
import com.omkar.jobaiassistant.dto.JobRequestDto;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    private final UserRepository userRepository;

    private final UserProfileRepository userProfileRepository;

    private final JobPreferenceRepository jobPreferenceRepository;

    public JobServiceImpl(
            JobRepository jobRepository,
            UserRepository userRepository,
            UserProfileRepository userProfileRepository,
            JobPreferenceRepository jobPreferenceRepository
    ) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.jobPreferenceRepository = jobPreferenceRepository;
    }

    @Override
    public List<JobResponseDto> getAllJobs() {

        List<Job> jobs =
                jobRepository.findByActiveTrue();

        return mapJobs(jobs);
    }

    @Override
    public List<JobResponseDto> searchJobs(
            String keyword
    ) {

        Set<Job> jobs =
                new LinkedHashSet<>();

        jobs.addAll(
                jobRepository
                        .findByTitleContainingIgnoreCaseAndActiveTrue(
                                keyword
                        )
        );

        jobs.addAll(
                jobRepository
                        .findByCompanyNameContainingIgnoreCaseAndActiveTrue(
                                keyword
                        )
        );

        jobs.addAll(
                jobRepository
                        .findByLocationContainingIgnoreCaseAndActiveTrue(
                                keyword
                        )
        );

        return mapJobs(
                new ArrayList<>(jobs)
        );
    }

    @Override
    public List<JobResponseDto> getRecommendedJobs(
            String email
    ) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        UserProfile profile =
                userProfileRepository
                        .findByUserId(user.getId())
                        .orElse(null);

        JobPreference preference =
                jobPreferenceRepository
                        .findByUserId(user.getId())
                        .orElse(null);

        List<Job> allJobs =
                jobRepository.findByActiveTrue();

        List<Job> recommendedJobs =
                new ArrayList<>();

        for (Job job : allJobs) {

            boolean matched = false;

            String jobTitle =
                    job.getTitle() == null
                            ? ""
                            : job.getTitle().toLowerCase();

            String jobLocation =
                    job.getLocation() == null
                            ? ""
                            : job.getLocation().toLowerCase();

            if (profile != null) {

                if (profile.getPreferredRoles() != null) {

                    String[] roles =
                            profile.getPreferredRoles()
                                    .split(",");

                    for (String role : roles) {

                        if (jobTitle.contains(
                                role.trim().toLowerCase()
                        )) {

                            matched = true;
                            break;
                        }
                    }
                }

                if (!matched &&
                        profile.getPreferredCities() != null) {

                    String[] cities =
                            profile.getPreferredCities()
                                    .split(",");

                    for (String city : cities) {

                        if (jobLocation.contains(
                                city.trim().toLowerCase()
                        )) {

                            matched = true;
                            break;
                        }
                    }
                }
            }

            if (preference != null) {

                if (!matched &&
                        preference.getPreferredRoles() != null) {

                    String[] roles =
                            preference.getPreferredRoles()
                                    .split(",");

                    for (String role : roles) {

                        if (jobTitle.contains(
                                role.trim().toLowerCase()
                        )) {

                            matched = true;
                            break;
                        }
                    }
                }

                if (!matched &&
                        preference.getPreferredLocations() != null) {

                    String[] locations =
                            preference.getPreferredLocations()
                                    .split(",");

                    for (String location : locations) {

                        if (jobLocation.contains(
                                location.trim().toLowerCase()
                        )) {

                            matched = true;
                            break;
                        }
                    }
                }
            }

            if (matched) {

                recommendedJobs.add(job);
            }
        }

        if (recommendedJobs.isEmpty()) {

            recommendedJobs = allJobs;
        }

        return mapJobs(recommendedJobs);
    }

    @Override
    public JobResponseDto createJob(
            JobRequestDto request
    ) {

        Job job = new Job();

        job.setTitle(request.getTitle());
        job.setCompanyName(request.getCompanyName());
        job.setLocation(request.getLocation());
        job.setEmploymentType(request.getEmploymentType());
        job.setExperienceLevel(request.getExperienceLevel());
        job.setSalary(request.getSalary());
        job.setDescription(request.getDescription());
        job.setJobUrl(request.getJobUrl());
        job.setRemoteAllowed(request.getRemoteAllowed());
        job.setActive(request.getActive());

        Job saved =
                jobRepository.save(job);

        return mapJob(saved);
    }

    @Override
    public JobResponseDto getJobById(
            Long id
    ) {

        Job job =
                jobRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Job not found"
                                )
                        );

        return mapJob(job);
    }

    @Override
    public JobResponseDto updateJob(
            Long id,
            JobRequestDto request
    ) {

        Job job =
                jobRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Job not found"
                                )
                        );

        job.setTitle(request.getTitle());
        job.setCompanyName(request.getCompanyName());
        job.setLocation(request.getLocation());
        job.setEmploymentType(request.getEmploymentType());
        job.setExperienceLevel(request.getExperienceLevel());
        job.setSalary(request.getSalary());
        job.setDescription(request.getDescription());
        job.setJobUrl(request.getJobUrl());
        job.setRemoteAllowed(request.getRemoteAllowed());
        job.setActive(request.getActive());

        Job updated =
                jobRepository.save(job);

        return mapJob(updated);
    }

    @Override
    public void deleteJob(
            Long id
    ) {

        Job job =
                jobRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Job not found"
                                )
                        );

        jobRepository.delete(job);
    }

    private JobResponseDto mapJob(
            Job job
    ) {

        JobResponseDto dto =
                new JobResponseDto();

        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setCompanyName(job.getCompanyName());
        dto.setLocation(job.getLocation());
        dto.setEmploymentType(job.getEmploymentType());
        dto.setExperienceLevel(job.getExperienceLevel());
        dto.setSalary(job.getSalary());
        dto.setDescription(job.getDescription());
        dto.setJobUrl(job.getJobUrl());
        dto.setRemoteAllowed(job.getRemoteAllowed());
        dto.setActive(job.getActive());

        return dto;
    }

    private List<JobResponseDto> mapJobs(
            List<Job> jobs
    ) {

        List<JobResponseDto> responses =
                new ArrayList<>();

        for (Job job : jobs) {

            JobResponseDto dto =
                    new JobResponseDto();

            dto.setId(job.getId());
            dto.setTitle(job.getTitle());
            dto.setCompanyName(job.getCompanyName());
            dto.setLocation(job.getLocation());
            dto.setEmploymentType(job.getEmploymentType());
            dto.setExperienceLevel(job.getExperienceLevel());
            dto.setSalary(job.getSalary());
            dto.setDescription(job.getDescription());
            dto.setJobUrl(job.getJobUrl());
            dto.setRemoteAllowed(job.getRemoteAllowed());
            dto.setActive(job.getActive());

            responses.add(dto);
        }

        return responses;
    }
}
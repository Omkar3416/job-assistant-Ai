package com.omkar.jobaiassistant.service.impl;

import com.omkar.jobaiassistant.entity.JobPreference;
import com.omkar.jobaiassistant.entity.User;
import com.omkar.jobaiassistant.repository.JobPreferenceRepository;
import com.omkar.jobaiassistant.service.JobApplicationService;
import com.omkar.jobaiassistant.service.JobDiscoveryService;
import com.omkar.jobaiassistant.service.JobImportService;
import com.omkar.jobaiassistant.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobDiscoveryServiceImpl
        implements JobDiscoveryService {

    private final JobPreferenceRepository jobPreferenceRepository;

    private final JobImportService jobImportService;

    private final JobRepository jobRepository;

    private final JobApplicationService jobApplicationService;

    public JobDiscoveryServiceImpl(
            JobPreferenceRepository jobPreferenceRepository,
            JobImportService jobImportService,
            JobRepository jobRepository,
            JobApplicationService jobApplicationService
    ) {
        this.jobPreferenceRepository =
                jobPreferenceRepository;
        this.jobImportService =
                jobImportService;
        this.jobRepository =
                jobRepository;
        this.jobApplicationService =
                jobApplicationService;
    }

    @Override
    public void discoverJobsForAllUsers() {

        List<JobPreference> preferences =
                jobPreferenceRepository
                        .findByAutoApplyEnabledTrue();

        System.out.println(
                "Auto Apply Users = "
                        + preferences.size()
        );

        for (JobPreference preference : preferences) {

            User user =
                    preference.getUser();

            try {

                System.out.println(
                        "Searching jobs for "
                                + user.getEmail()
                );

                int imported =
                        jobImportService
                                .importNaukriJobs(
                                        user.getEmail()
                                );

                System.out.println(
                        "Imported "
                                + imported
                                + " jobs"
                );

                System.out.println(
                        "Queueing imported jobs..."
                );


                System.out.println(
                        "Queueing completed."
                );

            } catch (Exception e) {

                System.out.println(
                        "Discovery failed for "
                                + user.getEmail()
                );

                e.printStackTrace();
            }
        }
    }
}
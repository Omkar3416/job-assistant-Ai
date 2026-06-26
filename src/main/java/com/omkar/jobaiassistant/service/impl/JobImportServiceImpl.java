package com.omkar.jobaiassistant.service.impl;

import com.omkar.jobaiassistant.dto.NaukriJobDto;
import com.omkar.jobaiassistant.entity.Job;
import com.omkar.jobaiassistant.entity.NaukriCredential;
import com.omkar.jobaiassistant.entity.User;
import com.omkar.jobaiassistant.repository.JobRepository;
import com.omkar.jobaiassistant.repository.NaukriCredentialRepository;
import com.omkar.jobaiassistant.repository.UserRepository;
import com.omkar.jobaiassistant.security.CredentialEncryptionService;
import com.omkar.jobaiassistant.service.JobImportService;
import com.omkar.jobaiassistant.service.PlaywrightClientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobImportServiceImpl
        implements JobImportService {

    private final UserRepository userRepository;

    private final NaukriCredentialRepository
            naukriCredentialRepository;

    private final CredentialEncryptionService
            credentialEncryptionService;

    private final PlaywrightClientService
            playwrightClientService;

    private final JobRepository
            jobRepository;

    public JobImportServiceImpl(
            UserRepository userRepository,
            NaukriCredentialRepository naukriCredentialRepository,
            CredentialEncryptionService credentialEncryptionService,
            PlaywrightClientService playwrightClientService,
            JobRepository jobRepository
    ) {
        this.userRepository = userRepository;
        this.naukriCredentialRepository =
                naukriCredentialRepository;
        this.credentialEncryptionService =
                credentialEncryptionService;
        this.playwrightClientService =
                playwrightClientService;
        this.jobRepository =
                jobRepository;
    }

    @Override
    public Integer importNaukriJobs(
            String userEmail
    ) {

        User user =
                userRepository
                        .findByEmail(userEmail)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "User not found"
                                )
                        );

        NaukriCredential credential =
                naukriCredentialRepository
                        .findByUserId(user.getId())
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Naukri credentials not found"
                                )
                        );

        String password =
                credentialEncryptionService.decrypt(
                        credential.getEncryptedPassword()
                );

        List<NaukriJobDto> jobs =
                playwrightClientService.searchJobs(
                        credential.getNaukriEmail(),
                        password,
                        "java-developer"
                );

        int savedCount = 0;

        for (NaukriJobDto dto : jobs) {

            if (
                    jobRepository.existsByJobUrl(
                            dto.getUrl()
                    )
            ) {

                continue;
            }

            Job job = new Job();

            job.setTitle(
                    dto.getTitle()
            );

            job.setCompanyName(
                    dto.getCompany()
            );

            job.setJobUrl(
                    dto.getUrl()
            );

            job.setSourcePortal(
                    "NAUKRI"
            );

            job.setActive(
                    true
            );

            jobRepository.save(
                    job
            );

            savedCount++;
        }

        return savedCount;
    }
}
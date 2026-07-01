package com.omkar.jobaiassistant.service.impl;

import com.omkar.jobaiassistant.entity.ApplicationStatus;
import com.omkar.jobaiassistant.entity.JobApplication;
import com.omkar.jobaiassistant.repository.JobApplicationRepository;
import com.omkar.jobaiassistant.service.JobAutoApplyService;
import org.springframework.stereotype.Service;
import com.omkar.jobaiassistant.dto.ApplyJobResultDto;
import com.omkar.jobaiassistant.entity.NaukriCredential;
import com.omkar.jobaiassistant.repository.NaukriCredentialRepository;
import com.omkar.jobaiassistant.security.CredentialEncryptionService;
import com.omkar.jobaiassistant.service.PlaywrightClientService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class JobAutoApplyServiceImpl
        implements JobAutoApplyService {

    private final JobApplicationRepository
            jobApplicationRepository;

    private final NaukriCredentialRepository
            naukriCredentialRepository;

    private final CredentialEncryptionService
            credentialEncryptionService;

    private final PlaywrightClientService
            playwrightClientService;

    public JobAutoApplyServiceImpl(
            JobApplicationRepository jobApplicationRepository,
            NaukriCredentialRepository naukriCredentialRepository,
            CredentialEncryptionService credentialEncryptionService,
            PlaywrightClientService playwrightClientService
    ) {

        this.jobApplicationRepository =
                jobApplicationRepository;

        this.naukriCredentialRepository =
                naukriCredentialRepository;

        this.credentialEncryptionService =
                credentialEncryptionService;

        this.playwrightClientService =
                playwrightClientService;
    }

    @Override
    public void processQueue() {

        List<JobApplication> queue =
                jobApplicationRepository
                        .findTop20ByStatusInOrderByQueuedAtAsc(
                                Arrays.asList(
                                        ApplicationStatus.READY_TO_APPLY,
                                        ApplicationStatus.FAILED,
                                        ApplicationStatus.QUOTA_WAITING
                                )
                        );

        for (JobApplication application : queue) {

            if (
                    application.getAttemptCount() != null
                            && application.getAttemptCount() >= 3
            ) {

                application.setStatus(
                        ApplicationStatus.FAILED
                );

                application.setLastError(
                        "Maximum retry limit reached."
                );

                jobApplicationRepository.save(
                        application
                );

                continue;
            }

            try {

                System.out.println(
                        "Processing: "
                                + application.getJobTitle()
                );
                NaukriCredential credential =
                        naukriCredentialRepository
                                .findByUserId(
                                        application
                                                .getUser()
                                                .getId()
                                )
                                .orElseThrow(
                                        () -> new RuntimeException(
                                                "Naukri credential not found"
                                        )
                                );

                String password =
                        credentialEncryptionService.decrypt(
                                credential.getEncryptedPassword()
                        );

                System.out.println("Calling Playwright...");

                ApplyJobResultDto result =
                        playwrightClientService.applyJob(
                                credential.getNaukriEmail(),
                                password,
                                application.getJobUrl()
                        );

                System.out.println("Playwright returned.");
                System.out.println(result);


                if (result.isQuotaReached()) {

                    application.setStatus(
                            ApplicationStatus.QUOTA_WAITING
                    );

                    application.setLastError(
                            "Daily Naukri quota reached"
                    );

                    jobApplicationRepository.save(
                            application
                    );

                    break;
                }

                if (result.isExternalApply()) {

                    application.setStatus(
                            ApplicationStatus.EXTERNAL_LINK
                    );

                    application.setManualApplyRequired(
                            true
                    );
                    application.setCompletedAt(
                            LocalDateTime.now()
                    );

                    application.setLastError(
                            result.getError()
                    );

                    jobApplicationRepository.save(
                            application
                    );

                    continue;
                }

                if (!result.isApplied()) {

                    markFailed(
                            application,
                            result.getError()
                    );

                    continue;
                }

                application.setStatus(
                        ApplicationStatus.APPLIED
                );

                application.setAutoApplied(
                        true
                );

                application.setCompletedAt(
                        LocalDateTime.now()
                );

                jobApplicationRepository.save(
                        application
                );

            } catch (Exception ex) {

                markFailed(
                        application,
                        ex.getMessage()
                );
            }
        }
    }

    private void markFailed(
            JobApplication application,
            String error
    ) {

        application.setStatus(
                ApplicationStatus.FAILED
        );

        application.setCompletedAt(
                LocalDateTime.now()
        );

        application.setLastError(
                error
        );

        application.setAttemptCount(
                application.getAttemptCount() == null
                        ? 1
                        : application.getAttemptCount() + 1
        );

        jobApplicationRepository.save(
                application
        );
    }
}
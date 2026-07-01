package com.omkar.jobaiassistant.service.impl;

import com.omkar.jobaiassistant.dto.NaukriJobDto;
import com.omkar.jobaiassistant.dto.JobMatchCheckRequestDto;
import com.omkar.jobaiassistant.dto.JobMatchCheckResponseDto;
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
import com.omkar.jobaiassistant.service.SearchKeywordService;
import com.omkar.jobaiassistant.service.JobApplicationService;

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

    private final SearchKeywordService
            searchKeywordService;

    private final JobApplicationService
            jobApplicationService;


    public JobImportServiceImpl(
            UserRepository userRepository,
            NaukriCredentialRepository naukriCredentialRepository,
            CredentialEncryptionService credentialEncryptionService,
            PlaywrightClientService playwrightClientService,
            JobRepository jobRepository,
            SearchKeywordService searchKeywordService,
            JobApplicationService jobApplicationService
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
        this.searchKeywordService =
                searchKeywordService;
        this.jobApplicationService =
                jobApplicationService;
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

        List<String> keywords =
                searchKeywordService.buildSearchKeywords(user);

        System.out.println(
                "Searching Naukri using keywords: "
                        + keywords
        );

        List<NaukriJobDto> jobs =
                playwrightClientService.searchJobs(
                        credential.getNaukriEmail(),
                        password,
                        keywords
                );

        if (jobs == null) {
            jobs = new java.util.ArrayList<>();
        }

        int savedCount = 0;

        for (NaukriJobDto dto : jobs) {

            /*
             * Skip already imported jobs
             */
            if (jobRepository.existsByUserIdAndJobUrl(
                    user.getId(),
                    dto.getUrl())) {

                continue;
            }

            /*
             * Skill Match + Already Applied + External Apply Check
             */
            if (!jobApplicationService.isEligibleForAutoApply(
                    user,
                    dto
            )) {

                /*
                 * External Apply job
                 */
                if (dto.getExternalApply() != null
                        && dto.getExternalApply()) {

                    Job job = new Job();

                    job.setUser(user);
                    job.setTitle(dto.getTitle());
                    job.setCompanyName(dto.getCompany());
                    job.setLocation(dto.getLocation());
                    job.setExperienceLevel(dto.getExperience());
                    job.setSalary(dto.getSalary());
                    job.setDescription(dto.getDescription());
                    job.setEasyApply(dto.getEasyApply());
                    job.setExternalApply(true);
                    job.setJobUrl(dto.getUrl());
                    job.setSourcePortal("NAUKRI");
                    job.setActive(true);

                    jobRepository.save(job);

                    jobApplicationService.saveExternalApplication(
                            user,
                            dto
                    );

                    savedCount++;
                }

                continue;
            }

            /*
             * Save matched job
             */
            Job job = new Job();

            job.setUser(user);
            job.setTitle(dto.getTitle());
            job.setCompanyName(dto.getCompany());
            job.setLocation(dto.getLocation());
            job.setExperienceLevel(dto.getExperience());
            job.setSalary(dto.getSalary());
            job.setDescription(dto.getDescription());
            job.setEasyApply(dto.getEasyApply());
            job.setExternalApply(dto.getExternalApply());
            job.setJobUrl(dto.getUrl());
            job.setSourcePortal("NAUKRI");
            job.setActive(true);

            Job savedJob =
                    jobRepository.save(job);

            savedCount++;

            /*
             * Auto Apply Immediately
             */
            jobApplicationService.applyJob(
                    savedJob.getId(),
                    userEmail
            );

        }

        return savedCount;
    }
}
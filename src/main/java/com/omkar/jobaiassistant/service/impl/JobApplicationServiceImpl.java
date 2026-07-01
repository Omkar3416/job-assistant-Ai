package com.omkar.jobaiassistant.service.impl;

import com.omkar.jobaiassistant.repository.SkillRepository;
import com.omkar.jobaiassistant.entity.Skill;
import com.omkar.jobaiassistant.dto.NaukriJobDto;
import com.omkar.jobaiassistant.dto.JobApplicationResponseDto;
import com.omkar.jobaiassistant.entity.ApplicationStatus;
import com.omkar.jobaiassistant.entity.Job;
import com.omkar.jobaiassistant.entity.JobApplication;
import com.omkar.jobaiassistant.entity.User;
import com.omkar.jobaiassistant.repository.JobApplicationRepository;
import com.omkar.jobaiassistant.repository.JobRepository;
import com.omkar.jobaiassistant.repository.UserRepository;
import com.omkar.jobaiassistant.service.JobApplicationService;
import org.springframework.stereotype.Service;
import com.omkar.jobaiassistant.dto.JobDashboardResponseDto;
import com.omkar.jobaiassistant.dto.ApplyJobResultDto;
import com.omkar.jobaiassistant.entity.NaukriCredential;
import com.omkar.jobaiassistant.repository.NaukriCredentialRepository;
import com.omkar.jobaiassistant.security.CredentialEncryptionService;
import java.time.LocalDateTime;
import com.omkar.jobaiassistant.service.PlaywrightClientService;
import com.omkar.jobaiassistant.dto.ApplyResultRequestDto;
import com.omkar.jobaiassistant.dto.JobMatchCheckRequestDto;
import com.omkar.jobaiassistant.dto.JobMatchCheckResponseDto;
import com.omkar.jobaiassistant.service.JobImportService;


import java.util.ArrayList;
import java.util.List;

@Service
public class JobApplicationServiceImpl
        implements JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;

    private final JobRepository jobRepository;

    private final UserRepository userRepository;


    private final SkillRepository skillRepository;

    private final PlaywrightClientService playwrightClientService;

    private final NaukriCredentialRepository credentialRepository;

    private final CredentialEncryptionService credentialEncryptionService;






    public JobApplicationServiceImpl(
            JobApplicationRepository jobApplicationRepository,
            JobRepository jobRepository,
            UserRepository userRepository,
            SkillRepository skillRepository,
            PlaywrightClientService playwrightClientService,
            NaukriCredentialRepository credentialRepository,
            CredentialEncryptionService credentialEncryptionService
    ) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
        this.playwrightClientService =
                playwrightClientService;

        this.credentialRepository =
                credentialRepository;

        this.credentialEncryptionService =
                credentialEncryptionService;

    }


    @Override
    public List<JobApplicationResponseDto>
    getMyApplications(
            String email
    ) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        List<JobApplication> applications =
                jobApplicationRepository
                        .findByUserId(
                                user.getId()
                        );

        List<JobApplicationResponseDto> responses =
                new ArrayList<>();

        for (JobApplication application : applications) {
            responses.add(
                    map(application, null)
            );
        }

        return responses;
    }

    @Override
    public JobDashboardResponseDto getDashboard(
            String email
    ) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        List<JobApplication> applications =
                jobApplicationRepository
                        .findByUserId(
                                user.getId()
                        );

        long totalApplications =
                applications.size();

        long appliedCount =
                applications.stream()
                        .filter(application ->
                                application.getStatus()
                                        == ApplicationStatus.APPLIED
                        )
                        .count();

        long failedCount =
                applications.stream()
                        .filter(application ->
                                application.getStatus()
                                        == ApplicationStatus.FAILED
                        )
                        .count();

        long waitingCount =
                applications.stream()
                        .filter(application ->
                                application.getStatus()
                                        == ApplicationStatus.WAITING_FOR_ANSWER
                        )
                        .count();

        JobDashboardResponseDto response =
                new JobDashboardResponseDto();

        response.setTotalApplications(
                totalApplications
        );

        response.setAppliedCount(
                appliedCount
        );

        response.setFailedCount(
                failedCount
        );

        response.setWaitingCount(
                waitingCount
        );

        return response;
    }

    @Override
    public JobApplicationResponseDto updateStatus(
            Long applicationId,
            ApplicationStatus status
    ) {

        JobApplication application =
                jobApplicationRepository
                        .findById(applicationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Application not found"
                                )
                        );

        application.setStatus(status);

        JobApplication updated =
                jobApplicationRepository.save(
                        application
                );

        return map(
                updated,
                "Application status updated successfully"
        );
    }

    private JobApplicationResponseDto map(
            JobApplication application,
            String message
    ) {

        JobApplicationResponseDto dto =
                new JobApplicationResponseDto();

        dto.setId(
                application.getId()
        );

        dto.setCompanyName(
                application.getCompanyName()
        );

        dto.setJobTitle(
                application.getJobTitle()
        );

        dto.setJobUrl(
                application.getJobUrl()
        );

        dto.setMatchScore(
                application.getMatchScore()
        );

        dto.setSourcePortal(
                application.getSourcePortal()
        );

        dto.setAutoApplied(
                application.getAutoApplied()
        );

        dto.setManualApplyRequired(
                application.getManualApplyRequired()
        );

        dto.setAttemptCount(
                application.getAttemptCount()
        );

        dto.setLastError(
                application.getLastError()
        );

        dto.setQueuedAt(
                application.getQueuedAt()
        );

        dto.setCompletedAt(
                application.getCompletedAt()
        );

        dto.setStatus(
                application.getStatus()
        );

        dto.setAppliedAt(
                application.getAppliedAt()
        );

        dto.setMessage(
                message
        );

        return dto;
    }

    @Override
    public void applyResult(
            ApplyResultRequestDto request
    ) {

        User user =
                userRepository.findByEmail(
                        request.getEmail()
                ).orElseThrow(
                        () -> new RuntimeException(
                                "User not found"
                        )
                );

        JobApplication application =
                jobApplicationRepository
                        .findFirstByUserIdAndCompanyNameAndJobTitleOrderByQueuedAtDesc(
                                user.getId(),
                                request.getCompany(),
                                request.getTitle()
                        )
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Application not found"
                                )
                        );

        application.setAttemptCount(
                application.getAttemptCount() == null
                        ? 1
                        : application.getAttemptCount() + 1
        );

        application.setCompletedAt(
                LocalDateTime.now()
        );

        if (request.isApplied()) {

            application.setStatus(
                    ApplicationStatus.APPLIED
            );

            application.setAppliedAt(
                    LocalDateTime.now()
            );

            application.setAutoApplied(true);

            application.setLastError(null);

        }
        else if (request.isQuotaReached()) {

            application.setStatus(
                    ApplicationStatus.QUOTA_WAITING
            );

            application.setLastError(
                    "Naukri daily quota reached."
            );

        }
        else if (request.isExternalApply()) {

            application.setStatus(
                    ApplicationStatus.EXTERNAL_LINK
            );

            application.setManualApplyRequired(true);

            application.setLastError(
                    request.getError()
            );

        }
        else {

            application.setStatus(
                    ApplicationStatus.FAILED
            );

            application.setLastError(
                    request.getError()
            );

        }

        jobApplicationRepository.save(
                application
        );

    }

    @Override
    public void searchAndApplyJobs(
            String email
    ) {

        throw new UnsupportedOperationException(
                "Use searchAndApplyJobsForAllUsers() instead."
        );

    }


    @Override
    public void searchAndApplyJobsForAllUsers() {

        throw new UnsupportedOperationException(
                "Use JobDiscoveryService.discoverJobsForAllUsers()"
        );

    }

    @Override
    public ApplyJobResultDto applyJob(
            Long jobId,
            String email
    ) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        Job job =
                jobRepository.findById(jobId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Job not found"
                                )
                        );

        NaukriCredential credential =
                credentialRepository
                        .findByUserId(
                                user.getId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Credential not found"
                                )
                        );

        String password =
                credentialEncryptionService.decrypt(
                        credential.getEncryptedPassword()
                );

        ApplyJobResultDto result =
                playwrightClientService.applyJob(
                        credential.getNaukriEmail(),
                        password,
                        job.getJobUrl()
                );

        JobApplication application =
                new JobApplication();

        application.setUser(user);

        application.setCompanyName(
                job.getCompanyName()
        );

        application.setJobTitle(
                job.getTitle()
        );

        application.setJobUrl(
                job.getJobUrl()
        );

        application.setSourcePortal(
                job.getSourcePortal()
        );

        application.setMatchScore(100.0);

        application.setQueuedAt(
                LocalDateTime.now()
        );

        application.setAttemptCount(
                application.getAttemptCount() == null
                        ? 1
                        : application.getAttemptCount() + 1
        );

        application.setCompletedAt(
                LocalDateTime.now()
        );

        if (result.isApplied()) {

            application.setStatus(
                    ApplicationStatus.APPLIED
            );

            application.setAppliedAt(
                    LocalDateTime.now()
            );

            application.setAutoApplied(true);

        } else if (result.isQuotaReached()) {

            application.setStatus(
                    ApplicationStatus.QUOTA_WAITING
            );

        } else if (result.isExternalApply()) {

            application.setStatus(
                    ApplicationStatus.EXTERNAL_LINK
            );

            application.setManualApplyRequired(true);

        } else {

            application.setStatus(
                    ApplicationStatus.FAILED
            );

        }

        application.setJobUrl(
                job.getJobUrl()
        );

        application.setLastError(
                result.getError()
        );

        jobApplicationRepository.save(
                application
        );

        return result;

    }

    @Override
    public void saveExternalApplication(
            User user,
            NaukriJobDto job
    ) {

        JobApplication application =
                new JobApplication();

        application.setUser(user);

        application.setCompanyName(
                job.getCompany()
        );

        application.setJobTitle(
                job.getTitle()
        );

        application.setJobUrl(
                job.getUrl()
        );

        application.setSourcePortal(
                "NAUKRI"
        );

        application.setMatchScore(
                100.0
        );

        application.setQueuedAt(
                LocalDateTime.now()
        );

        application.setManualApplyRequired(
                true
        );

        application.setAutoApplied(
                false
        );

        application.setStatus(
                ApplicationStatus.EXTERNAL_LINK
        );

        application.setLastError(
                "Please apply manually using the external link."
        );

        jobApplicationRepository.save(
                application
        );

    }

    @Override
    public boolean isEligibleForAutoApply(
            User user,
            NaukriJobDto job
    ) {

        return isJobMatched(user, job)
                && !alreadyApplied(user, job)
                && !isExternalApplyJob(job);

    }

    @Override
    public JobMatchCheckResponseDto canApply(
            JobMatchCheckRequestDto request
    ) {

        User user =
                userRepository.findByEmail(
                        request.getEmail()
                ).orElseThrow(
                        () -> new RuntimeException(
                                "User not found"
                        )
                );

        NaukriJobDto job =
                new NaukriJobDto();

        job.setTitle(
                request.getTitle()
        );

        job.setCompany(
                request.getCompany()
        );

        job.setDescription(
                request.getDescription()
        );

        job.setExperience(
                request.getExperience()
        );

        job.setSalary(
                request.getSalary()
        );

        job.setLocation(
                request.getLocation()
        );

        job.setUrl(
                request.getJobUrl()
        );

        JobMatchCheckResponseDto response =
                new JobMatchCheckResponseDto();

//        if (isExternalApplyJob(job)) {
//
//
//            response.setMatched(false);
//            response.setExternalApply(true);
//            response.setReason(
//                    "External Apply"
//            );
//
//            return response;
//
//        }

        if (!isJobMatched(user, job)) {

            response.setMatched(false);
            response.setReason(
                    "Skill mismatch"
            );

            return response;

        }

        if (alreadyApplied(user, job)) {

            response.setMatched(false);
            response.setReason(
                    "Already applied"
            );

            return response;

        }

        response.setMatched(true);
        response.setReason("MATCHED");

        return response;


    }



    /**
     * Detects whether job should be treated as external apply
     * (LinkedIn, company career page, etc.)
     */
    private boolean isExternalApplyJob(NaukriJobDto job) {

        if (job == null) {
            return false;
        }

        String url = job.getUrl();

        if (url == null) {
            return false;
        }

        url = url.toLowerCase();

        return url.contains("linkedin")
                || url.contains("instahyre")
                || url.contains("company")
                || url.contains("greenhouse")
                || url.contains("workday")
                || url.contains("lever");
    }

    private boolean isJobMatched(
            User user,
            NaukriJobDto job
    ) {

        List<Skill> skills =
                skillRepository.findByUserId(
                        user.getId()
                );

        String searchable =
                normalize(
                        job.getTitle()
                                + " "
                                +job.getCompany()
                                + " "
                                + job.getDescription()
                );

        for (Skill skill : skills) {

            if (
                    searchable.contains(
                            normalize(skill.getSkillName())
                    )
            ) {

                return true;

            }

        }

        return false;
    }

    private boolean alreadyApplied(
            User user,
            NaukriJobDto job
    ) {

        return jobApplicationRepository
                .existsByUserIdAndCompanyNameAndJobTitle(
                        user.getId(),
                        job.getCompany(),
                        job.getTitle()
                );

    }

    private JobApplication createApplication(
            User user,
            NaukriJobDto job
    ) {

        JobApplication application =
                new JobApplication();

        application.setUser(user);

        application.setCompanyName(
                job.getCompany()
        );

        application.setJobTitle(
                job.getTitle()
        );

        application.setJobUrl(
                job.getUrl()
        );

        application.setSourcePortal(
                "NAUKRI"
        );

        application.setMatchScore(
                100.0
        );

        application.setQueuedAt(
                LocalDateTime.now()
        );

        application.setAttemptCount(1);

        return application;

    }

    private String normalize(
            String value
    ) {

        if (value == null) {

            return "";

        }

        return value
                .toLowerCase()
                .replace("-", "")
                .replace("_", "")
                .replace(" ", "");

    }


}
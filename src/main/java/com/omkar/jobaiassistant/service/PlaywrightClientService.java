package com.omkar.jobaiassistant.service;

import com.omkar.jobaiassistant.dto.NaukriJobDto;
import com.omkar.jobaiassistant.dto.ApplyJobResultDto;
import java.util.List;

public interface PlaywrightClientService {

    List<NaukriJobDto> searchJobs(
            String email,
            String password,
            String keyword
    );

    ApplyJobResultDto applyJob(
            String email,
            String password,
            String jobUrl
    );
}
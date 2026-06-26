package com.omkar.jobaiassistant.scheduler;

import com.omkar.jobaiassistant.service.JobAutoApplyService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AutoApplyScheduler {

    private final JobAutoApplyService
            jobAutoApplyService;

    public AutoApplyScheduler(
            JobAutoApplyService jobAutoApplyService
    ) {
        this.jobAutoApplyService =
                jobAutoApplyService;
    }

    @Scheduled(
            fixedDelay = 10000
    )
    public void runQueue() {

        System.out.println("==================================");
        System.out.println("Scheduler Started");
        System.out.println("==================================");

        jobAutoApplyService.processQueue();

        System.out.println("Scheduler Finished");
    }
}
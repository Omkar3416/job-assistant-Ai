package com.omkar.jobaiassistant.scheduler;

import com.omkar.jobaiassistant.service.JobDiscoveryService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobAutoApplyScheduler {

    private final JobDiscoveryService jobDiscoveryService;

    public JobAutoApplyScheduler(
            JobDiscoveryService jobDiscoveryService
    ) {
        this.jobDiscoveryService = jobDiscoveryService;
    }

    private boolean running = false;

    @Scheduled(fixedDelay = 30000)
    public void autoApplyJobs() {

        if (running) {
            System.out.println("Previous run still executing...");
            return;
        }

        running = true;

        try {

            System.out.println("AUTO APPLY STARTED");

            jobDiscoveryService.discoverJobsForAllUsers();


        } finally {

            running = false;

            System.out.println("AUTO APPLY FINISHED");

        }
    }

}
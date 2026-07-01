//package com.omkar.jobaiassistant.scheduler;
//
//import com.omkar.jobaiassistant.service.JobDiscoveryService;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DailyJobDiscoveryScheduler {
//
//    private final JobDiscoveryService
//            jobDiscoveryService;
//
//    public DailyJobDiscoveryScheduler(
//            JobDiscoveryService jobDiscoveryService
//    ) {
//        this.jobDiscoveryService =
//                jobDiscoveryService;
//    }
//
//    /*
//     * Every day at 6:00 AM
//     */
////    @Scheduled(
////            cron = "0 0 6 * * *"
////    )
//
////    @Scheduled(
////            fixedDelay = 60000
////    )
//    public void discoverJobs() {
//
//        System.out.println(
//                "======================================="
//        );
//
//        System.out.println(
//                "Daily Job Discovery Started"
//        );
//
//        System.out.println(
//                "======================================="
//        );
//
//        jobDiscoveryService.discoverJobsForAllUsers();
//
//        System.out.println(
//                "Daily Job Discovery Finished"
//        );
//    }
//}
package com.omkar.jobaiassistant;
import org.springframework.scheduling.annotation.EnableScheduling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableScheduling
public class JobAiAssistantApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobAiAssistantApplication.class, args);
    }

}

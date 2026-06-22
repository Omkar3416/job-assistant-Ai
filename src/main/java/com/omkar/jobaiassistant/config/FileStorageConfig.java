package com.omkar.jobaiassistant.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class FileStorageConfig {

    public static final String RESUME_UPLOAD_DIR =
            "uploads/resumes";

    @PostConstruct
    public void init() {

        File directory =
                new File(RESUME_UPLOAD_DIR);

        if (!directory.exists()) {

            directory.mkdirs();

            System.out.println(
                    "Created directory: "
                            + RESUME_UPLOAD_DIR
            );
        }
    }
}
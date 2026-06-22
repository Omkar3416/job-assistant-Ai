package com.omkar.jobaiassistant.service;

import com.omkar.jobaiassistant.dto.ResumeResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface ResumeService {

    ResumeResponseDto uploadResume(
            MultipartFile file,
            String storageMode
    );
    ResumeResponseDto getResume(
            String email
    );

    void deleteResume(
            String email
    );
}
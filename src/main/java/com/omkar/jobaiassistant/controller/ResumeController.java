package com.omkar.jobaiassistant.controller;

import com.omkar.jobaiassistant.dto.ResumeResponseDto;
import com.omkar.jobaiassistant.service.ResumeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(
            ResumeService resumeService
    ) {
        this.resumeService = resumeService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResumeResponseDto>
    uploadResume(
            @RequestParam("file")
            MultipartFile file,

            @RequestParam(
                    value = "storageType",
                    defaultValue = "SERVER"
            )
            String storageType
    ) {

        System.out.println(
                "========== RESUME UPLOAD =========="
        );

        System.out.println(
                "FILE NAME = "
                        + file.getOriginalFilename()
        );

        System.out.println(
                "FILE SIZE = "
                        + file.getSize()
        );

        System.out.println(
                "CONTENT TYPE = "
                        + file.getContentType()
        );

        ResumeResponseDto response =
                resumeService.uploadResume(
                        file,
                        storageType
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ResumeResponseDto>
    getResume(
            Authentication authentication
    ) {

        System.out.println(
                "========== GET RESUME =========="
        );

        System.out.println(
                "AUTHENTICATION = "
                        + authentication
        );

        if (authentication == null) {

            throw new RuntimeException(
                    "Authentication not found"
            );
        }

        String email =
                authentication.getName();

        System.out.println(
                "LOGGED IN EMAIL = "
                        + email
        );

        ResumeResponseDto response =
                resumeService.getResume(email);

        return ResponseEntity.ok(response);
    }
    @DeleteMapping
    public ResponseEntity<String>
    deleteResume(
            Authentication authentication
    ) {

        String email =
                authentication.getName();

        resumeService.deleteResume(
                email
        );

        return ResponseEntity.ok(
                "Resume deleted successfully"
        );
    }
}
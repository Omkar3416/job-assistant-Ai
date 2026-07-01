package com.omkar.jobaiassistant.controller;

import com.omkar.jobaiassistant.dto.ApplicationAnswerRequestDto;
import com.omkar.jobaiassistant.dto.ApplicationAnswerResponseDto;
import com.omkar.jobaiassistant.service.ApplicationAnswerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/application-answer")
public class ApplicationAnswerController {

    private final ApplicationAnswerService applicationAnswerService;

    public ApplicationAnswerController(
            ApplicationAnswerService applicationAnswerService
    ) {
        this.applicationAnswerService = applicationAnswerService;
    }

    @PostMapping
    public ResponseEntity<ApplicationAnswerResponseDto> answerQuestion(
            Authentication authentication,
            @RequestBody ApplicationAnswerRequestDto request
    ) {

        if (authentication == null) {

            throw new RuntimeException(
                    "Authentication not found"
            );

        }

        String email =
                authentication.getName();

        String answer =
                applicationAnswerService.generateAnswer(
                        email,
                        request.getQuestion()
                );

        return ResponseEntity.ok(
                new ApplicationAnswerResponseDto(
                        answer
                )
        );

    }

}
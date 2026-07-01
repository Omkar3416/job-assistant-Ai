package com.omkar.jobaiassistant.controller;

import com.omkar.jobaiassistant.dto.JobMatchCheckRequestDto;
import com.omkar.jobaiassistant.dto.JobMatchCheckResponseDto;
import com.omkar.jobaiassistant.service.PlaywrightAutomationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/playwright")
public class PlaywrightController {

    private final PlaywrightAutomationService
            playwrightAutomationService;

    public PlaywrightController(
            PlaywrightAutomationService playwrightAutomationService
    ) {

        this.playwrightAutomationService =
                playwrightAutomationService;

    }

    @PostMapping("/check-job")
    public JobMatchCheckResponseDto checkJob(

            @RequestBody
            JobMatchCheckRequestDto request

    ) {

        return playwrightAutomationService
                .checkJob(request);

    }

}
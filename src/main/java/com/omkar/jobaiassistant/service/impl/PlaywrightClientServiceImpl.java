package com.omkar.jobaiassistant.service.impl;

import com.omkar.jobaiassistant.dto.NaukriJobDto;
import com.omkar.jobaiassistant.service.PlaywrightClientService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.omkar.jobaiassistant.dto.ApplyJobResultDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlaywrightClientServiceImpl
        implements PlaywrightClientService {

    private final RestTemplate restTemplate;

    public PlaywrightClientServiceImpl(
            RestTemplate restTemplate
    ) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<NaukriJobDto> searchJobs(


            String email,
            String password,
            List<String> keywords
    )


    {

        System.out.println("==========================");
        System.out.println("Playwright search called");
        System.out.println("Keywords = " + keywords);
        System.out.println("==========================");

        String url =
                "http://localhost:3001/naukri/search-jobs";

        Map<String, Object> request =
                new HashMap<>();

        request.put(
                "email",
                email
        );

        request.put(
                "password",
                password
        );

        request.put(
                "keywords",
                keywords
        );

        HttpHeaders headers =
                new HttpHeaders();

        headers.setContentType(
                MediaType.APPLICATION_JSON
        );

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(
                        request,
                        headers
                );

        ResponseEntity<List<NaukriJobDto>> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        entity,
                        new ParameterizedTypeReference<List<NaukriJobDto>>() {}
                );

        return response.getBody();

    }


    @Override
    public ApplyJobResultDto applyJob(
            String email,
            String password,
            String jobUrl
    ) {

        String url =
                "http://localhost:3001/naukri/apply-job";

        Map<String, String> request =
                new HashMap<>();

        request.put(
                "email",
                email
        );

        request.put(
                "password",
                password
        );

        request.put(
                "jobUrl",
                jobUrl
        );

        HttpHeaders headers =
                new HttpHeaders();

        headers.setContentType(
                MediaType.APPLICATION_JSON
        );

        HttpEntity<Map<String, String>> entity =
                new HttpEntity<>(
                        request,
                        headers
                );

        ResponseEntity<ApplyJobResultDto> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        entity,
                        ApplyJobResultDto.class
                );

        return response.getBody();
    }
}
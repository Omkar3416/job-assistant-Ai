package com.omkar.jobaiassistant.controller;

import com.omkar.jobaiassistant.dto.NaukriCredentialRequestDto;
import com.omkar.jobaiassistant.dto.NaukriCredentialResponseDto;
import com.omkar.jobaiassistant.service.NaukriCredentialService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/naukri-credentials")
public class NaukriCredentialController {

    private final NaukriCredentialService
            naukriCredentialService;

    public NaukriCredentialController(
            NaukriCredentialService naukriCredentialService
    ) {
        this.naukriCredentialService =
                naukriCredentialService;
    }

    @PostMapping
    public ResponseEntity<NaukriCredentialResponseDto>
    saveCredentials(
            @RequestBody
            NaukriCredentialRequestDto request,
            Authentication authentication
    ) {

//        System.out.println(
//                "EMAIL = " +
//                        request.getNaukriEmail()
//        );

//        System.out.println(
//                "PASSWORD = " +
//                        request.getNaukriPassword()
//        );

        return ResponseEntity.ok(
                naukriCredentialService.saveCredentials(
                        authentication.getName(),
                        request
                )
        );
    }

    @GetMapping
    public ResponseEntity<NaukriCredentialResponseDto>
    getCredentials(
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                naukriCredentialService.getCredentials(
                        authentication.getName()
                )
        );
    }
//    @GetMapping("/test-decrypt")
//    public ResponseEntity<String> testDecrypt(
//            Authentication authentication
//    ) {
//
//        return ResponseEntity.ok(
//                naukriCredentialService
//                        .getDecryptedPassword(
//                                authentication.getName()
//                        )
//        );
//    }
}
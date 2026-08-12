package com.essel.portfolio.controller;

import com.essel.portfolio.dto.ContactRequest;
import com.essel.portfolio.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/contact")
@RequiredArgsConstructor
public class ContactController {

    private final EmailService emailService;

    @Operation(
            summary = "Submit contact form",
            description = "Receives a contact form submission from the portfolio website and sends it to my support email."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Contact form submitted successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "500", description = "Failed to send email")
    })

    @PostMapping
    public ResponseEntity<Map<String, String>> createContact(@RequestBody ContactRequest contactRequest) {
        emailService.sendContactEmail(contactRequest);
        return ResponseEntity.accepted().body(Map.of(
                "message", "Your message has been sent"
        ));
    }
}

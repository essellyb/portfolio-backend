package com.essel.portfolio.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Project type is required")
    private String projectType;

    @NotBlank(message = "Budget range is required")
    private String budgetRange;

    @NotBlank(message = "Message is required")
    private String message;
}

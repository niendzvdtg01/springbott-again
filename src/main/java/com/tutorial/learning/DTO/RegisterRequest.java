package com.tutorial.learning.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank
    @Email
    @Size(max = 254)
    String email,

    @NotBlank
    @Size(
        min = 15,
        max = 128,
        message = "password must contain 15-128 characters"
    )
    String password
) {}
package com.tutorial.learning.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTaskRequest(
    @NotBlank(message = "title must not be blank")
    @Size(max = 120, message = "title must not exceed 120 characters")
    String title,

    @Size(max = 1000, message = "description must not exceed 1000 characters")
    String description
) {} 

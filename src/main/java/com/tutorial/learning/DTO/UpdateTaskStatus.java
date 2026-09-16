package com.tutorial.learning.DTO;

import com.tutorial.learning.Enum.TaskStatus;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateTaskStatus(
        @NotNull(message = "status must ot be bull") 
        TaskStatus status,
        @NotNull(message = "version must not be bull")
        @PositiveOrZero (message = "version must not be negative")
        Long version
) {}

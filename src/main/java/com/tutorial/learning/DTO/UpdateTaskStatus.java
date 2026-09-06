package com.tutorial.learning.DTO;

import com.tutorial.learning.Enum.TaskStatus;

import jakarta.validation.constraints.NotNull;

public record UpdateTaskStatus(
        @NotNull(message = "status must ot be bull") TaskStatus status) {
}

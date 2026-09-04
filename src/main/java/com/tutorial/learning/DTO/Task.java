package com.tutorial.learning.DTO;

import com.tutorial.learning.Enum.TaskStatus;

public record Task(
    long id,
    String title,
    String description,
    TaskStatus status
) {}

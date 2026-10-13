package com.tutorial.learning.DTO;

import java.time.Instant;

import com.tutorial.learning.Enum.TaskStatus;

public record TaskStatusChangedEvent(
        String eventId,
        Long taskId,
        Long ownerId,
        TaskStatus previousStatus,
        TaskStatus currentStatus,
        long taskVersion,
        Instant occurAt) {
}

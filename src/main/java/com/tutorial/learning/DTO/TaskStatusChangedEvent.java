package com.tutorial.learning.DTO;

import java.time.Instant;

public record TaskStatusChangedEvent(
                String eventId,
                Long taskId,
                Long ownerId,
                String previousStatus,
                String currentStatus,
                long taskVersion,
                Instant occurAt) {
}

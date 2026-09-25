package com.tutorial.learning.DTO;

import java.time.Instant;
import java.util.UUID;

public record TaskStatusChangedEvent(UUID eventId, long taskId,long ownerId, String previousStatus, String currentSatus, long taskVersion, Instant occurAt) {
} 

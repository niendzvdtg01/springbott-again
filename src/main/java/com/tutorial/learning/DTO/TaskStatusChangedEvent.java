package com.tutorial.learning.DTO;

import java.time.Instant;

public record TaskStatusChangedEvent(String eventId, long taskId,long ownerId, String previousStatus, String currentSatus, long taskVersion, Instant occurAt) {
} 

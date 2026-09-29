package com.tutorial.learning.DTO;

import java.time.Instant;

import com.tutorial.learning.Entity.TaskEntity;
import com.tutorial.learning.Entity.UserEntity;

public record TaskStatusChangedEvent(String eventId, TaskEntity taskId, UserEntity ownerId, String previousStatus,
        String currentSatus, long taskVersion, Instant occurAt) {
}

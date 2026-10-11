package com.tutorial.learning.RabbitMq;

import java.time.Instant;

import org.springframework.stereotype.Component;

import com.tutorial.learning.Config.RabbitMqConfig;
import com.tutorial.learning.DTO.TaskStatusChangedEvent;
import com.tutorial.learning.Entity.OutboxEventsEntity;
import com.tutorial.learning.Enum.OutboxStatus;
import com.tutorial.learning.Repository.OutboxEventsRepository;

import tools.jackson.databind.ObjectMapper;

@Component
public class OutBoxWriter {
    private final OutboxEventsRepository outboxEventsRepository;
    private final ObjectMapper objectMapper;

    public OutBoxWriter(OutboxEventsRepository outboxEventsRepository, ObjectMapper objectMapper) {
        this.outboxEventsRepository = outboxEventsRepository;
        this.objectMapper = objectMapper;
    }

    public void append(TaskStatusChangedEvent taskStatusChangedEvent) {
        try {
            String payload = objectMapper.writeValueAsString(taskStatusChangedEvent);
            OutboxEventsEntity outboxEventsEntity = new OutboxEventsEntity(
                    taskStatusChangedEvent.eventId().toString(),
                    "TASK",
                    "TASK_STATUS_CHANGED",
                    RabbitMqConfig.TASK_EXCHANGE,
                    RabbitMqConfig.ROUTING_KEY,
                    payload,
                    OutboxStatus.PENDING,
                    1,
                    taskStatusChangedEvent.occurAt(),
                    Instant.now(),
                    "Error");
            outboxEventsRepository.save(outboxEventsEntity);
        } catch (Exception e) {
            throw new IllegalStateException("Cannot serialize exception!", e);
        }
    }
}

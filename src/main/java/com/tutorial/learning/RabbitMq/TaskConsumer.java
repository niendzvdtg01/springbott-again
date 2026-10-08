package com.tutorial.learning.RabbitMq;

import java.time.Instant;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.tutorial.learning.Config.RabbitMqConfig;
import com.tutorial.learning.DTO.TaskStatusChangedEvent;
import com.tutorial.learning.Entity.TaskActivityEntity;
import com.tutorial.learning.Repository.TaskActivityRepository;

@Component
public class TaskConsumer {
    private final TaskActivityRepository taskActivityRepository;

    public TaskConsumer(TaskActivityRepository taskActivityRepository) {
        this.taskActivityRepository = taskActivityRepository;
    }

    @RabbitListener(queues = RabbitMqConfig.ACTIVITY_QUEUE)
    public void handle(TaskStatusChangedEvent event) {
        if (taskActivityRepository.existsByEventId(event.eventId())) {
            return;
        }
        try {
            TaskActivityEntity task = new TaskActivityEntity();
            task.setEventId(event.eventId());
            task.setTaskId(event.taskId());
            task.setOwner(event.ownerId());
            task.setPreviousStatus(event.previousStatus());
            task.setCurrentStatus(event.currentSatus());
            task.setTaskVersion(event.taskVersion());
            task.setOccuredAt(event.occurAt());
            task.setProcessedAt(Instant.now());
            taskActivityRepository.save(task);
        } catch (DataIntegrityViolationException duplicate) {
            throw new DataIntegrityViolationException("duplicate error!!");
        }
    }
}

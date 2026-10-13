package com.tutorial.learning.RabbitMq;

import java.time.Instant;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.tutorial.learning.Config.RabbitMqConfig;
import com.tutorial.learning.DTO.TaskStatusChangedEvent;
import com.tutorial.learning.Entity.TaskActivityEntity;
import com.tutorial.learning.Entity.TaskEntity;
import com.tutorial.learning.Entity.UserEntity;
import com.tutorial.learning.Repository.TaskActivityRepository;
import com.tutorial.learning.Repository.TaskRepository;
import com.tutorial.learning.Repository.UserRepository;
import com.tutorial.learning.exception.TaskNotFoundException;

@Component
public class TaskConsumer {
    private final TaskActivityRepository taskActivityRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskConsumer(TaskActivityRepository taskActivityRepository, TaskRepository taskRepository,
            UserRepository userRepository) {
        this.taskActivityRepository = taskActivityRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @RabbitListener(queues = RabbitMqConfig.ACTIVITY_QUEUE)
    public void handle(TaskStatusChangedEvent event) {
        if (taskActivityRepository.existsByEventId(event.eventId())) {
            return;
        }
        try {
            TaskActivityEntity task = new TaskActivityEntity();
            TaskEntity taskEntity = taskRepository.findById(event.taskId())
                    .orElseThrow(() -> new TaskNotFoundException(event.taskId()));

            UserEntity userEntity = userRepository.findById()

            task.setEventId(event.eventId());
            task.setTaskId(taskEntity);
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

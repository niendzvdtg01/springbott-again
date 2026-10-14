package com.tutorial.learning.RabbitMq;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tutorial.learning.Config.RabbitMqConfig;
import com.tutorial.learning.Entity.OutboxEventsEntity;
import com.tutorial.learning.Enum.OutboxStatus;
import com.tutorial.learning.Repository.OutboxEventsRepository;

@Component
public class TaskEventPublisher {
    private final RabbitTemplate rabbitTemplate;
    private final OutboxEventsRepository outboxRepository;

    public TaskEventPublisher(RabbitTemplate rabbitTemplate, OutboxEventsRepository outboxRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.outboxRepository = outboxRepository;
    }

    @Scheduled(fixedDelay = 10000)
    public void publish() {
        List<OutboxEventsEntity> events = outboxRepository
                .findTop50ByStatusOrderByCreatedAtAsc(OutboxStatus.PENDING);
        System.out.println(OutboxStatus.PENDING.toString());
        for (OutboxEventsEntity event : events) {
            try {
                rabbitTemplate.convertAndSend(RabbitMqConfig.TASK_EXCHANGE, RabbitMqConfig.ROUTING_KEY,
                        event.getPayload());
                event.setStatus(OutboxStatus.PUBLISHED);
                outboxRepository.save(event);
            } catch (Exception e) {
                throw new IllegalStateException("Failed to publish event: " + event.getId(), e);
            }
        }
    }
}

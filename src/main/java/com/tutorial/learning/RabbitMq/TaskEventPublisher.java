package com.tutorial.learning.RabbitMq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import com.tutorial.learning.Repository.OutboxRepository;

import com.tutorial.learning.Config.RabbitMqConfig;
import com.tutorial.learning.DTO.TaskStatusChangedEvent;

@Component 
public class TaskEventPublisher {
    private final RabbitTemplate rabbitTemplate;
    private final OutboxRepository outboxRepository;

    public TaskEventPublisher(RabbitTemplate rabbitTemplate, OutboxRepository outboxRepository){
        this.rabbitTemplate = rabbitTemplate;
        this.outboxRepository = outboxRepository;
    }

    @Scheduled(fixedDelay = 10000)
    public void publish(){
        List<OutboxEventEntity> events = outboxRepository.findTop50ByStatusOrderByCreatedAtAsc(OutboxEventStatus.PENDING);
        for(OutboxEventEntity event : events){
            try{
                rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE, RabbitMqConfig.ROUTING_KEY, event.getPayload());
                event.setStatus(OutboxEventStatus.PUBLISHED);
                outboxRepository.save(event);
            } catch (Exception e){
                throw new IllegalStateException("Failed to publish event: " + event.getId(), e);
            }
        }
    }
}

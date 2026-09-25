package com.tutorial.learning.RabbitMq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.tutorial.learning.Config.RabbitMqConfig;
import com.tutorial.learning.DTO.TaskStatusChangedEvent;

@Component 
public class TaskEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    public TaskEventPublisher(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publish(TaskStatusChangedEvent event){
        rabbitTemplate.convertAndSend(RabbitMqConfig.TASK_EXCHANGE, RabbitMqConfig.ROUTING_KEY, event);
    }
}

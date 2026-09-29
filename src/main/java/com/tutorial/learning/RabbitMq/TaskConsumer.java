package com.tutorial.learning.RabbitMq;

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
    public TaskConsumer(TaskActivityRepository taskActivityRepository){
        this.taskActivityRepository = taskActivityRepository;
    }

    @RabbitListener(queues = RabbitMqConfig.ACTIVITY_QUEUE)
    public void handle(TaskStatusChangedEvent event){
        if(!taskActivityRepository.existsByEventId(event.eventId())){
            return;
        }
        try{ 
            TaskActivityEntity task = new TaskActivityEntity();
            task.setEventId(event.eventId());
            taskActivityRepository.saveAndFlush(task);
        }catch(DataIntegrityViolationException duplicate){
            throw new DataIntegrityViolationException("duplicate error!!");
        }
    }
}

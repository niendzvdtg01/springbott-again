package com.tutorial.learning.Config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration 
public class RabbitMqConfig {
    public static final String TASK_EXCHANGE = "taskflow.events";

    public static final String ACTIVITY_QUEUE = "taskflow.task-activity";

    public static final String ROUTING_KEY = "task.satus.changed";
    
    
    @Bean 
    DirectExchange taskExchange(){
        return new DirectExchange(TASK_EXCHANGE, true, false);
    }
    @Bean 
    Queue taskActivityQueue(){
        return QueueBuilder.durable(ACTIVITY_QUEUE).build();
    }

    @Bean 
    Binding taskActivityBinding(Queue taskActivityQueue, DirectExchange taskExchange){
        return BindingBuilder.bind(taskActivityQueue).to(taskExchange).with(ROUTING_KEY);
    }

    @Bean 
    JacksonJsonMessageConverter messageConverter(){
        return new JacksonJsonMessageConverter();
    }
}

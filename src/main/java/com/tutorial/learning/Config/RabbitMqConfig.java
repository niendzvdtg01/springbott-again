package com.tutorial.learning.Config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    public static final String TASK_EXCHANGE = "taskflow.events";

    public static final String ACTIVITY_QUEUE = "taskflow.task-activity";

    public static final String ROUTING_KEY = "task.status.changed";

    public static final String DEAD_EXCHANGE = "taskflow.dead";

    public static final String DEAD_QUEUE = "taskflow.task-activity.dead";

    public static final String DEAD_ROUTING_KEY = "task.status.dead";

    // task exchange
    @Bean
    DirectExchange taskExchange() {
        return new DirectExchange(TASK_EXCHANGE, true, false);
    }

    // dead exchange
    @Bean
    DirectExchange deadExchange() {
        return new DirectExchange(DEAD_EXCHANGE, true, false);
    }

    @Bean
    Queue taskActivityQueue() {
        return QueueBuilder.durable(ACTIVITY_QUEUE).deadLetterExchange(DEAD_EXCHANGE)
                .deadLetterRoutingKey(DEAD_ROUTING_KEY).build();
    }

    @Bean
    Queue deadQueueActivity() {
        return QueueBuilder.durable(DEAD_QUEUE).build();
    }

    @Bean
    Binding taskActivityBinding(@Qualifier("taskActivityQueue") Queue taskActivityQueue,
            @Qualifier("taskExchange") DirectExchange taskExchange) {
        return BindingBuilder.bind(taskActivityQueue).to(taskExchange).with(ROUTING_KEY);
    }

    @Bean
    Binding deadActivityBinding(@Qualifier("deadQueueActivity") Queue deadQueue,
            @Qualifier("deadExchange") DirectExchange deadExchange) {
        return BindingBuilder.bind(deadQueue).to(deadExchange).with(DEAD_ROUTING_KEY);
    }

    @Bean
    MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }
}

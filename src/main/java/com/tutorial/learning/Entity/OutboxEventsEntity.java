package com.tutorial.learning.Entity;

import com.tutorial.learning.Enum.OutboxStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * OutboxEventsEntity
 */
@Entity 
@Table(name = "outbox_events") 
public class OutboxEventsEntity {
    @Id 
    @Column(length = 36)
    private String id;
    @Column(nullable = false)
    private String aggregateType;
    @Column(nullable = false)
    private String eventType;
    @Column(nullable = false)
    private String exchangeName;
    @Column(nullable = false)
    private String routingKey;
    @Column(nullable = false, columnDefinition = "json")
    private  String payload;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)   
    private OutboxStatus status;
    

    
}
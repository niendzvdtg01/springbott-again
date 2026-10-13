package com.tutorial.learning.Entity;

import java.time.Instant;

import com.tutorial.learning.Enum.OutboxStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * OutboxEventsEntity
 */
@Getter
@Setter
@Entity
@Table(name = "outbox_events")
public class OutboxEventsEntity {
    @Id
    @Column(name = "id", length = 36)
    private String id;
    @Column(name = "aggregate_type", nullable = false)
    private String aggregateType;
    @Column(name = "aggregate_id", nullable = false)
    private long aggregateId;
    @Column(name = "event_type", nullable = false)
    private String eventType;
    @Column(name = "exchange_name", nullable = false)
    private String exchangeName;
    @Column(name = "routing_key", nullable = false)
    private String routingKey;
    @Column(name = "payload", nullable = false, columnDefinition = "json")
    private String payload;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OutboxStatus status;
    @Column(name = "attempts", nullable = false)
    private int attemps;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    @Column(name = "published_at", nullable = false)
    private Instant publishAt;

    @Column(name = "last_error", length = 1000)
    private String lastError;

    protected OutboxEventsEntity() {

    }

    public OutboxEventsEntity(String id, String aggregateType, String eventType, String exchangeName, String routingKey,
            String payload, OutboxStatus status, int attemps, Instant createdAt, Instant publishAt, String lastError) {
        this.id = id;
        this.aggregateType = aggregateType;
        this.eventType = eventType;
        this.exchangeName = exchangeName;
        this.routingKey = routingKey;
        this.payload = payload;
        this.status = status;
        this.attemps = attemps;
        this.createdAt = createdAt;
        this.publishAt = publishAt;
        this.lastError = lastError;
    }

}
package com.tutorial.learning.Entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "task_activity")
public class TaskActivityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "event_id", unique = true)
    private String eventId;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "task_id")
    private TaskEntity taskId;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id")
    private UserEntity owner;
    @Column(name = "previous_status")
    private String previousStatus;
    @Column(name = "current_status")
    private String currentStatus;
    @Column(name = "task_version")
    private long taskVersion;
    @Column(name = "occurred_at")
    private Instant occuredAt;
    @Column(name = "processed_at")
    private Instant processedAt;
}

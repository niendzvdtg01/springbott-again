package com.tutorial.learning.Entity;

import java.time.Instant;

import com.tutorial.learning.Enum.TaskStatus;
import com.tutorial.learning.exception.InvalidTaskTransitionException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "tasks")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, length = 120)
    private String title;
    @Column(length = 1000)
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TaskStatus status;
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected TaskEntity() {

    }

    public TaskEntity(String title, String description, TaskStatus status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }

    @PrePersist
    void beforeInsert() {
        if (createdAt == null) {
            this.createdAt = Instant.now();
        }
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return this.status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    // check valid status and change status if it's valid
    public void changesStatusTo(TaskStatus nextStatus) {
        if (nextStatus == status) {
            return;
        }
        boolean validTransaction = (status == TaskStatus.TODO && nextStatus == TaskStatus.IN_PROGRESS)
                || (status == TaskStatus.IN_PROGRESS && nextStatus == TaskStatus.DONE);
        if (!validTransaction) {
            throw new InvalidTaskTransitionException(status, nextStatus);
        }
        this.status = nextStatus;
    }

}

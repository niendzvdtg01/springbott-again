 package com.tutorial.learning.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tutorial.learning.DTO.TaskStatusChangedEvent;

public interface TaskActivityRepository extends JpaRepository<TaskStatusChangedEvent, Long>{
    boolean exiexistsByEventId(UUID eventId);
}
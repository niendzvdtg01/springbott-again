package com.tutorial.learning.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tutorial.learning.Entity.TaskEntity;
import com.tutorial.learning.Enum.TaskStatus;


public interface TaskRepository extends JpaRepository<TaskEntity, Long>{
    Optional<TaskEntity> findByIdAndOwnerId(long id, long userid);
    Page<TaskEntity> findAllByOwnerId(long id, Pageable pageable);
    Page<TaskEntity> findAllByOwnerIdAndStatus(long userId, TaskStatus status, Pageable pageable);
} 

package com.tutorial.learning.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tutorial.learning.Entity.TaskEntity;


public interface TaskRepository extends JpaRepository<TaskEntity, Long>{
    Optional<TaskEntity> findByIdAndOwnerId(long id, long userid);
    List<TaskEntity> findAllByOwnerIdOrderByIdDesc(long id);
} 

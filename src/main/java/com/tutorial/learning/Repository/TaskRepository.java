package com.tutorial.learning.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tutorial.learning.Entity.TaskEntity;

public interface TaskRepository extends JpaRepository<TaskEntity, Long>{

} 

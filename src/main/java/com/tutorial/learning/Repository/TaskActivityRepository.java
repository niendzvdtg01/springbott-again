 package com.tutorial.learning.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tutorial.learning.Entity.TaskActivityEntity;

public interface TaskActivityRepository extends JpaRepository<TaskActivityEntity, Long>{
    boolean existsByEventId(String eventId);
}
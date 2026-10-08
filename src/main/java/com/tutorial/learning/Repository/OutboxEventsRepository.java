package com.tutorial.learning.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tutorial.learning.Entity.OutboxEventsEntity;
import com.tutorial.learning.Enum.OutboxStatus;

public interface OutboxEventsRepository extends JpaRepository<OutboxEventsEntity, String> {
    List<OutboxEventsEntity> findTop50ByStatusOrderByCreatedAtAsc(OutboxStatus status);
}

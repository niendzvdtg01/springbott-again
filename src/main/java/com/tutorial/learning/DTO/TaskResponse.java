package com.tutorial.learning.DTO;

import java.io.Serializable;

import com.tutorial.learning.Enum.TaskStatus;

/**
 * TaskResponse
 */
public record TaskResponse(
      long id,
      String title,
      TaskStatus status,
      long version) implements Serializable {
}
package com.tutorial.learning.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(long id) {
        super(id + " not found");
    }
}

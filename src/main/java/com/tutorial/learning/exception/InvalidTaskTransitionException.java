package com.tutorial.learning.exception;

import com.tutorial.learning.Enum.TaskStatus;

public class InvalidTaskTransitionException extends RuntimeException {

    public InvalidTaskTransitionException(TaskStatus curent, TaskStatus next) {
        super("Cannot change task status from" + curent + " to " + next);
    }
}
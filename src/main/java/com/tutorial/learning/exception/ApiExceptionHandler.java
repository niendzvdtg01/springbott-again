package com.tutorial.learning.exception;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice 
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
    @ExceptionHandler(TaskNotFoundException.class)
    ProblemDetail handleTaskNotFound(TaskNotFoundException exception){
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
        problem.setTitle("Task not found!");
        problem.setType(URI.create( "https://api.taskflow.dev/problems/task-not-found"));
        problem.setProperty("code", "TASK_NOT_FOUND");
        return problem;
    }

    @ExceptionHandler(InvalidTaskTransitionException.class)
    ProblemDetail handleInvalidTransition(InvalidTaskTransitionException exception) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,exception.getMessage());

        problem.setTitle("Invalid task transition");
        problem.setType(URI.create("https://api.taskflow.dev/problems/" + "invalid-task-transition"));
        problem.setProperty("code","INVALID_TASK_TRANSITION");

        return problem;
    }
    @ExceptionHandler(EmailAlreadyUsedException.class)
    ProblemDetail handleEmailAlreadyUsedException(){
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,"An account already uses this email");
        problem.setTitle("Email Already exists");
        problem.setProperty("code", "EMAIL_ALREADY_USED");
        return problem;
    }
}
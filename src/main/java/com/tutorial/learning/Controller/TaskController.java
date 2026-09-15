package com.tutorial.learning.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tutorial.learning.DTO.CreateTaskRequest;
import com.tutorial.learning.DTO.TaskResponse;
import com.tutorial.learning.DTO.UpdateTaskStatus;
import com.tutorial.learning.Enum.TaskStatus;
import com.tutorial.learning.Service.TaskService;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("api/v1/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("{id}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable long id) {
        if(id != 1){
            return ResponseEntity.notFound().build();
        }
        TaskResponse task = new TaskResponse(
            1,
            "HTTP request life cycle", 
            TaskStatus.TODO
        );
        return ResponseEntity.ok(task);
    }

    @PostMapping("/test")
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody CreateTaskRequest task, Authentication authentication) {
        TaskResponse created = taskService.create(task, getUserIdByAuth(authentication));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(created.id()).toUri();

        return ResponseEntity.created(location).body(created);
    }

    @GetMapping("/test/{id}")
    public TaskResponse findById(@PathVariable long id, Authentication authentication) {
        return taskService.findById(id, getUserIdByAuth(authentication));
    }
    @GetMapping("/test/all")
    public List<TaskResponse> findAll(Authentication authentication) {
        return taskService.findAll(getUserIdByAuth(authentication));
    }

    @PutMapping("test/{id}")
    public TaskResponse putSatus(@PathVariable long id,@Valid  @RequestBody UpdateTaskStatus request, Authentication authentication) {
        return taskService.updateTask(id, request, getUserIdByAuth(authentication));
    }

    public Long getUserIdByAuth(Authentication authentication){
        return (Long)authentication.getPrincipal();
    }
    
}


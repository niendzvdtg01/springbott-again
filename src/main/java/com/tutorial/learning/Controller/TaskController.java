package com.tutorial.learning.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tutorial.learning.DTO.CreateTaskRequest;
import com.tutorial.learning.DTO.PageResponse;
import com.tutorial.learning.DTO.TaskResponse;
import com.tutorial.learning.DTO.UpdateTaskStatus;
import com.tutorial.learning.Enum.TaskStatus;
import com.tutorial.learning.Service.TaskService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.net.URI;
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
            TaskStatus.TODO,
            1L
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
    public PageResponse<TaskResponse> findAll(@RequestParam(defaultValue = "0")
    @Min(value = 0, message = "page must be at least 0")
    int page,
    @RequestParam(defaultValue = "10")
    @Min(value = 1, message = "size must be at least 1")
    @Max(value = 100, message = "size must not exceed 100")
    int size,
    @RequestParam(required = false)
    TaskStatus status,

    Authentication authentication) {
        return taskService.findAll(getUserIdByAuth(authentication), status, page, size);
    }

    @PutMapping("test/{id}")
    public TaskResponse putSatus(@PathVariable long id,@Valid  @RequestBody UpdateTaskStatus request, Authentication authentication) {
        return taskService.updateTask(id, request, getUserIdByAuth(authentication));
    }

    public Long getUserIdByAuth(Authentication authentication){
        return (Long)authentication.getPrincipal();
    }
    
}
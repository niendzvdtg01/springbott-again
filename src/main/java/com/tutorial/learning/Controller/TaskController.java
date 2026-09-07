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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping("api/v1/tasks")
public class TaskController {
    private final TaskService taskService;

    TaskController(TaskService taskService) {
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
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody CreateTaskRequest task) {
        TaskResponse created = taskService.create(task);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(created.id()).toUri();

        return ResponseEntity.created(location).body(created);
    }

    @GetMapping("/test/{id}")
    public ResponseEntity<TaskResponse> findById(@PathVariable long id) {
        return taskService.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("test/{id}")
    public TaskResponse putSatus(@PathVariable long id,@Valid  @RequestBody UpdateTaskStatus request) {
        return taskService.updateTask(id, request);
    }
    
}


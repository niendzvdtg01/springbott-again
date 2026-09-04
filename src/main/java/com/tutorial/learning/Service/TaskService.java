package com.tutorial.learning.Service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tutorial.learning.DTO.CreateTaskRequest;
import com.tutorial.learning.DTO.TaskResponse;
import com.tutorial.learning.Entity.TaskEntity;
import com.tutorial.learning.Enum.TaskStatus;
import com.tutorial.learning.Repository.TaskRepository;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }
    public TaskResponse create(CreateTaskRequest createTaskRequest){
        TaskEntity task = new TaskEntity(
            createTaskRequest.title(),
            createTaskRequest.description().trim(),
            TaskStatus.DONE
        );
        TaskEntity saved = taskRepository.save(task);
        return new TaskResponse(
            saved.getId(),
            saved.getTitle(),
            saved.getStatus()
        );
    }
    
    public Optional<TaskResponse> findById(long id){
        return taskRepository.findById(id).map(task -> new TaskResponse(task.getId(), task.getTitle(), task.getStatus()));
    }
}

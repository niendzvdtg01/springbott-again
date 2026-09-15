package com.tutorial.learning.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tutorial.learning.DTO.CreateTaskRequest;
import com.tutorial.learning.DTO.PageResponse;
import com.tutorial.learning.DTO.TaskResponse;
import com.tutorial.learning.DTO.UpdateTaskStatus;
import com.tutorial.learning.Entity.TaskEntity;
import com.tutorial.learning.Entity.UserEntity;
import com.tutorial.learning.Enum.TaskStatus;
import com.tutorial.learning.Repository.TaskRepository;
import com.tutorial.learning.Repository.UserRepository;
import com.tutorial.learning.exception.TaskNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    public TaskService(TaskRepository taskRepository, UserRepository userRepository){
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }
    public TaskResponse create(CreateTaskRequest createTaskRequest, Long userId){

        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("Authenticated no longer exist!"));
        TaskEntity task = new TaskEntity(
            createTaskRequest.title(),
            createTaskRequest.description().trim(),
            TaskStatus.TODO,
            user
        );
        TaskEntity saved = taskRepository.save(task);
        return new TaskResponse(
            saved.getId(),
            saved.getTitle(),
            saved.getStatus()
        );
    }
    @Transactional
    public TaskResponse findById(long id, long userId){
        TaskEntity task = taskRepository.findByIdAndOwnerId(id, userId).orElseThrow(()->new TaskNotFoundException(id));
        return new TaskResponse(task.getId(), task.getTitle(), task.getStatus());
    }   
    @Transactional 
    public TaskResponse updateTask(long id, UpdateTaskStatus request, long userId){
        TaskEntity task = taskRepository.findByIdAndOwnerId(id, userId).orElseThrow(()-> new TaskNotFoundException(id));
        task.changeStatusTo(request.status());
        return new TaskResponse(task.getId(), task.getTitle(), task.getStatus());
    }
    @Transactional 
    public PageResponse<TaskResponse> findAll(long userId, TaskStatus status, int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));
        Page<TaskEntity> tasks;
        if(status == null){
            tasks = taskRepository.findAllByOwnerId(userId, pageable);
        }else{
            tasks = taskRepository.findAllByOwnerIdAndStatus(userId, status, pageable);
        }

        Page<TaskResponse> responses = tasks.map(task -> new TaskResponse(task.getId(), task.getTitle(), task.getStatus()));
        return PageResponse.from(responses); 
    }

}

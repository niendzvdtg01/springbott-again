// package com.tutorial.learning.Repository;

// import java.util.Map;
// import java.util.Optional;
// import java.util.concurrent.ConcurrentHashMap;
// import java.util.concurrent.atomic.AtomicLong;
// import org.springframework.stereotype.Repository;

// import com.tutorial.learning.DTO.Task;

// @Repository
// public class InMemoryTaskRepository implements TaskRepository{
//     private final Map<Long, Task> tasks = new ConcurrentHashMap<>();
//     private final AtomicLong sequence = new AtomicLong();
//     @Override
//     public Task save(Task task){
//         long id = sequence.getAndIncrement();

//         Task saved = new Task(id, task.title(), task.description(), task.status());
//         tasks.put(id, saved);

//         return saved;
//     }
//     @Override
//     public Optional<Task> findById(long id){
//         return Optional.ofNullable(tasks.get(id));
//     }
// }

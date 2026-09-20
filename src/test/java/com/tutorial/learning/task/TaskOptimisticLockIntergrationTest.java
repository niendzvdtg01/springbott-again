package com.tutorial.learning.task;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;

import com.tutorial.learning.Entity.TaskEntity;
import com.tutorial.learning.Entity.UserEntity;
import com.tutorial.learning.Enum.TaskStatus;
import com.tutorial.learning.Enum.UserRole;
import com.tutorial.learning.Repository.TaskRepository;
import com.tutorial.learning.Repository.UserRepository;

/**
 * TaskOptimisticLockIntergrationTest
 */
@SpringBootTest
@Testcontainers
public class TaskOptimisticLockIntergrationTest {
    @Container
    @ServiceConnection
    static final MySQLContainer MYSQL = new MySQLContainer("mysql:8.4");

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    private long taskId;

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();
        userRepository.deleteAll();

        UserEntity owner = userRepository.save(new UserEntity("nien@example.com", "pasword-test", UserRole.USER));

        TaskEntity taskEntity = taskRepository
                .saveAndFlush(new TaskEntity("test", "test", com.tutorial.learning.Enum.TaskStatus.TODO, owner));
        taskId = taskEntity.getId();
    }

    @Test
    void shouldAllowOnlyOneConcurrentUpdate() throws Exception {
        CyclicBarrier barrier = new CyclicBarrier(2);
        AtomicInteger success = new AtomicInteger();
        AtomicInteger conflicts = new AtomicInteger();

        ExecutorService executor = Executors.newFixedThreadPool(2);

        Runnable updateTask = () -> {
            TransactionTemplate transaction = new TransactionTemplate(platformTransactionManager);
            try {
                transaction.executeWithoutResult(ignored -> {
                    TaskEntity taskEntity = taskRepository.findById(taskId).orElseThrow();

                    await(barrier);
                    taskEntity.changeStatusTo(TaskStatus.IN_PROGRESS);

                    taskRepository.flush();
                });

                success.incrementAndGet();
            } catch (OptimisticLockingFailureException exception) {
                conflicts.incrementAndGet();
            }
        };

        try {
            Future<?> first = executor.submit(updateTask);
            Future<?> second = executor.submit(updateTask);

            first.get(10, TimeUnit.SECONDS);
            second.get(10, TimeUnit.SECONDS);
        } finally {
            executor.shutdownNow();
        }

        assertThat(success.get()).isEqualTo(1);
        assertThat(conflicts.get()).isEqualTo(1);

        TaskEntity stored = taskRepository.findById(taskId).orElseThrow();

        assertThat(stored.getStatus()).isEqualTo(TaskStatus.IN_PROGRESS);

        assertThat(stored.getVersion()).isEqualTo(1L);
    }

    private static void await(CyclicBarrier barrier) {
        try {
            barrier.await(5, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(ex);
        } catch (BrokenBarrierException | TimeoutException exception) {
            throw new IllegalStateException(exception);
        }
    }

}
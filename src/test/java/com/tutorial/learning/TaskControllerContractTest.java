package com.tutorial.learning;

import static org.mockito.Mockito.mock;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.tutorial.learning.Controller.TaskController;
import com.tutorial.learning.DTO.TaskResponse;
import com.tutorial.learning.DTO.UpdateTaskStatus;
import com.tutorial.learning.Enum.TaskStatus;
import com.tutorial.learning.Service.TaskService;
import com.tutorial.learning.exception.ApiExceptionHandler;
import com.tutorial.learning.exception.TaskNotFoundException;

public class TaskControllerContractTest {
    private TaskService taskService;
    private RestTestClient client;
    private PasswordEncoder passwordEncoder;


    @BeforeEach 
    void setUp(){
        taskService = mock(TaskService.class);
        passwordEncoder = new  Argon2PasswordEncoder(16, 32, 1, 19456, 2);
        TaskController controller = new TaskController(taskService);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new ApiExceptionHandler()).build();

        client = RestTestClient.bindTo(mockMvc).build();

    }
    @Test 
    void shouldUpdateTaskStatus(){
        given(taskService.updateTask(eq(1L), any(UpdateTaskStatus.class), eq(1L))).willReturn(new TaskResponse(1L,
                "Write tests",
                TaskStatus.IN_PROGRESS, 0));
         client.put()
            .uri("/api/v1/tasks/test/1")
            .contentType(MediaType.APPLICATION_JSON)
            .body(Map.of("status", TaskStatus.IN_PROGRESS))
            .exchange()
            .expectStatus().isOk()
            .expectHeader()
                .contentTypeCompatibleWith(
                    MediaType.APPLICATION_JSON
                )
            .expectBody()
            .jsonPath("$.id").isEqualTo(1)
            .jsonPath("$.status")
                .isEqualTo("IN_PROGRESS");

        then(taskService).should().updateTask(eq(1L), any(UpdateTaskStatus.class), eq(1L));
    }
    @Test 
    void shouldReturnProblemDetailWhenTaskDoesNotExists(){
        given(taskService.updateTask(eq(999L), any(UpdateTaskStatus.class), eq(1L))).willThrow(new TaskNotFoundException(999L));
        client.put().uri("/api/v1/tasks/test/999")
        .contentType(MediaType.APPLICATION_JSON)
        .body(Map.of("status", TaskStatus.IN_PROGRESS))
        .exchange()
        .expectStatus().isNotFound()
        .expectHeader()
        .contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON)
        .expectBody()
        .jsonPath("$.status").isEqualTo(404)
        .jsonPath("$.code")
            .isEqualTo("TASK_NOT_FOUND")
        .jsonPath("$.title")
            .isEqualTo("Task not found!")
        .jsonPath("$.instance")
            .isEqualTo("/api/v1/tasks/test/999");

    }
    @Test
    void encodedPasswordShouldMatchRawPassword() {
        String raw = "A-long-demo-password-2026!";
        String encoded = passwordEncoder.encode(raw);

        assertThat(encoded).isNotEqualTo(raw);
        assertThat(passwordEncoder.matches(raw, encoded)).isTrue();
        assertThat(passwordEncoder.matches("wrong-password",encoded)).isFalse();
}
}

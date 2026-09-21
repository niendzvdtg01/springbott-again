package com.tutorial.learning;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.tutorial.learning.Entity.TaskEntity;
import com.tutorial.learning.Entity.UserEntity;
import com.tutorial.learning.Enum.TaskStatus;
import com.tutorial.learning.Enum.UserRole;
import com.tutorial.learning.exception.InvalidTaskTransitionException;

public class TestEntity {

    @Test
    public void shoudChangeFromTodoToInProgressTest() {
        UserEntity user = new UserEntity("test", "test", UserRole.ADMIN);
        TaskEntity task = new TaskEntity("test", "test", TaskStatus.TODO, user);
        task.changeStatusTo(TaskStatus.IN_PROGRESS);
        assertThat(task.getStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
        System.out.println("SUCCESSFULLY!!!");
    }

    @Test
    public void shoudChangeFromInProgressToDone() {
        UserEntity user = new UserEntity("test", "test", UserRole.ADMIN);
        TaskEntity task = new TaskEntity("test", "test", TaskStatus.IN_PROGRESS, user);
        task.changeStatusTo(TaskStatus.DONE);
        assertThat(task.getStatus()).isEqualTo(TaskStatus.DONE);
        System.out.println("SUCCESSFULLY!!!");
    }

    @Test
    public void shouldRejectMovingDirectlyFromTodoToDone() {
        UserEntity user = new UserEntity("test", "test", UserRole.ADMIN);
        TaskEntity task = new TaskEntity("Write test", "test case 2", TaskStatus.TODO, user);
        assertThatThrownBy(() -> task.changeStatusTo(TaskStatus.DONE))
                .isInstanceOf(InvalidTaskTransitionException.class).hasMessageContaining("TODO")
                .hasMessageContaining("DONE");
        assertThat(task.getStatus()).isEqualTo(TaskStatus.TODO);
    }
}

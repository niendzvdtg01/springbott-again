package com.tutorial.learning;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.tutorial.learning.Entity.TaskEntity;
import com.tutorial.learning.Enum.TaskStatus;
import com.tutorial.learning.exception.InvalidTaskTransitionException;

public class TestEntity {
    @Test 
    public void shoudChangeFromTodoToInProgressTest(){
        TaskEntity task = new TaskEntity("test", "test", TaskStatus.TODO);
        task.changeStatusTo(TaskStatus.IN_PROGRESS);
        assertThat(task.getStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
        System.out.println("SUCCESSFULLY!!!");
    }

    @Test 
    public void shoudChangeFromInProgressToDone(){
        TaskEntity task = new TaskEntity("test", "test", TaskStatus.IN_PROGRESS);
        task.changeStatusTo(TaskStatus.DONE);
        assertThat(task.getStatus()).isEqualTo(TaskStatus.DONE);
        System.out.println("SUCCESSFULLY!!!");
    }
    @Test 
    public void shouldRejectMovingDirectlyFromTodoToDone(){
        TaskEntity task = new TaskEntity("Write test", "test case 2", TaskStatus.TODO);
        assertThatThrownBy(()-> task.changeStatusTo(TaskStatus.DONE)).isInstanceOf(InvalidTaskTransitionException.class).hasMessageContaining("TODO").hasMessageContaining("DONE");
        assertThat(task.getStatus()).isEqualTo(TaskStatus.TODO);
    }
}

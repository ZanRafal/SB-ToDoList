package com.example.todoapp.logic;

import com.example.todoapp.model.TaskGroup;
import com.example.todoapp.model.TaskGroupRepository;
import com.example.todoapp.model.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class TaskGroupServiceTest {

    @Test
    @DisplayName("should throw IllegalStateException when undone tasks exists")
    void toggleGroup_hasUndoneTasks_throwsIllegalStateException() {
        TaskRepository mockTaskRepository = taskRepositoryReturning(true);

        var toTest = new TaskGroupService(null, mockTaskRepository);

        var exception = catchThrowable(() -> toTest.toggleGroup(1));

        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("undone tasks");
    }

    @Test
    @DisplayName("should throw IllegalArgumentException when no group for a given id")
    void toggleGroup_noGroup_throwsIllegalArgumentException() {
        TaskRepository mockTaskRepository = taskRepositoryReturning(false);

        TaskGroupRepository mockTaskGroupRepository = mock(TaskGroupRepository.class);
        when(mockTaskGroupRepository.findById(anyInt())).thenReturn(Optional.empty());

        var toTest = new TaskGroupService(mockTaskGroupRepository, mockTaskRepository);

        var exception = catchThrowable(() -> toTest.toggleGroup(1));

        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id not found");
    }

    @Test
    @DisplayName("should toggle group")
    void toggleGroup_worksAsExpected() {
        TaskRepository mockTaskRepository = taskRepositoryReturning(false);

        var group = new TaskGroup();
        var beforeToggle = group.isDone();

        TaskGroupRepository mockTaskGroupRepository = mock(TaskGroupRepository.class);
        when(mockTaskGroupRepository.findById(anyInt())).
                thenReturn(Optional.of(group));

        var toTest = new TaskGroupService(mockTaskGroupRepository, mockTaskRepository);

        toTest.toggleGroup(0);

        assertThat(group.isDone()).isEqualTo(!beforeToggle);
    }

    private TaskRepository taskRepositoryReturning(final boolean result) {
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(result);
        return mockTaskRepository;
    }
}
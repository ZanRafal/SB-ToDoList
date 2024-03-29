package com.example.todoapp.model.projection;

import com.example.todoapp.model.Task;
import com.example.todoapp.model.TaskGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNull;

class GroupReadModelTest {

    @Test
    @DisplayName("should create null deadline for group when no deadlines")
    void constructor_noDeadlines_createsNullDeadline() {
        var source = new TaskGroup();
        source.setDescription("foo");
        source.setTasks(Set.of(new Task("bar", null)));

        var result = new GroupReadModel(source);

        assertNull(result.getDeadline());
    }

}
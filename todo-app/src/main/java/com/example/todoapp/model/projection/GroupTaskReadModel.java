package com.example.todoapp.model.projection;

import com.example.todoapp.model.Task;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupTaskReadModel {
    private boolean done;
    private String description;

    public GroupTaskReadModel(Task task) {
        description = task.getDescription();
        done = task.isDone();
    }
}

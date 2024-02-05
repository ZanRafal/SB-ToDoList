package com.example.todoapp.model.event;

import com.example.todoapp.model.Task;
import lombok.Getter;

import java.time.Clock;
import java.time.Instant;

@Getter
public abstract class TaskEvent {
    public static TaskEvent changed(Task source) {
        return source.isDone() ?
                new TaskDone(source.getId(), Clock.systemDefaultZone()) :
                new TaskUndone(source.getId(), Clock.systemDefaultZone());
    }

    private final int taskId;
    private final Instant occurrence;

    TaskEvent(int taskId, Clock clock) {
        this.taskId = taskId;
        this.occurrence = Instant.now();
    }

    @Override
    public String toString() {
        return "TaskEvent{" +
                "taskId=" + taskId +
                ", occurrence=" + occurrence +
                '}';
    }
}

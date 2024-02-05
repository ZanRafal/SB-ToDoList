package com.example.todoapp.model;

import com.example.todoapp.model.event.TaskEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@NoArgsConstructor
public class Task {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Setter
    @Getter
    @NotBlank(message = "Task description must not be blank!")
    private String description;
    @Getter
    private boolean done;
    @Getter
    @Column()
    private LocalDateTime deadline;
    @ManyToOne
    @JoinColumn(name = "task_group_id")
    private TaskGroup group;
    @Embedded
    @AttributeOverrides(
            {
                    @AttributeOverride(column = @Column(name ="updatedOn"), name = "updatedOn"),
                    @AttributeOverride(column = @Column(name = "createdOn"), name = "createdOn")
            }
    )
    private final Audit audit = new Audit();

    public Task(String description, LocalDateTime deadline) {
        this(description, deadline, null);
    }

    public Task(String description, LocalDateTime deadline, TaskGroup group) {
        this.description = description;
        this.deadline = deadline;
        if(group != null) {
            this.group = group;
        }
    }

    void setId(int id) {
        this.id = id;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    Audit getAudit() {
        return audit;
    }

    public TaskEvent toggle() {
        this.done = !this.done;
        return TaskEvent.changed(this);
    }

    public void updateFrom(final Task source) {
        description = source.description;
        done = source.done;
        deadline = source.deadline;
        group = source.group;
    }
}


package com.example.todoapp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "task_groups")
@NoArgsConstructor
public class TaskGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Setter
    @Getter
    @NotBlank(message = "Task groups description must not be blank!")
    private String description;
    private boolean done;
    @Embedded
    private Audit audit = new Audit();
    @Setter
    @Getter
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
    private Set<Task> tasks;
    @ManyToOne
    @JoinColumn(name = "project_id")
    @Getter
    @Setter
    private Project project;

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

}


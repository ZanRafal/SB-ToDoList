package com.example.todoapp.model;

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
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank(message = "Task description must not be blank!")
    private String description;
    private boolean done;
    @Column()
    private LocalDateTime deadline;
    //@Transient nie chcemy zapisywać tego pola w bazie
    @Embedded
    @AttributeOverrides(//nadpisanie nazw kolumn z klasy dziedziczącej
            {
                    @AttributeOverride(column = @Column(name ="updatedOn"), name = "updatedOn"),
                    @AttributeOverride(column = @Column(name = "createdOn"), name = "createdOn")
            }
    )
    private Audit audit = new Audit();

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void updateFrom(final Task source) {
        description = source.description;
        done = source.done;
        deadline = source.deadline;
    }
}


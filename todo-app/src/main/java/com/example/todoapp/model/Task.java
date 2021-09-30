package com.example.todoapp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import javax.transaction.Transactional;
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
    @Getter
    @Setter
    private LocalDateTime deadline;
    //@Transient nie chcemy zapisywać tego pola w bazie
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

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

    @PrePersist//funckcja odpali się przed zapisem do bazy danych, słyży do insertu, zapisu a bazie
    void prePersist() {
        createdOn = LocalDateTime.now();
    }

    @PreUpdate//dokladanie do encji
    void preMerge() {
        updatedOn = LocalDateTime.now();
    }
}


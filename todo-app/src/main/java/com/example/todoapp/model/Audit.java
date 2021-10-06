package com.example.todoapp.model;

import javax.persistence.Embeddable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Embeddable
public class Audit {
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    @PrePersist
    //funckcja odpali się przed zapisem do bazy danych, słyży do insertu, zapisu a bazie
    void prePersist() {
        createdOn = LocalDateTime.now();
    }

    @PreUpdate
    //dokladanie do encji
    void preMerge() {
        updatedOn = LocalDateTime.now();
    }
}

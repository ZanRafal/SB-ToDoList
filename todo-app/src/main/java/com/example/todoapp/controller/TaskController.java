package com.example.todoapp.controller;

import com.example.todoapp.model.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;

@RepositoryRestController
class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskRepository.class);
    private final TaskRepository repository;

    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    ResponseEntity<?> readAllTasks() {
        logger.warn("Displaying all tasks!");
        return ResponseEntity.ok(repository.findAll());
    }
}

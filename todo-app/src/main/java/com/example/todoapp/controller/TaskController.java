package com.example.todoapp.controller;

import com.example.todoapp.model.TaskRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RepositoryRestController
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskRepository.class);
    @NonNull private final TaskRepository repository;

    //@RequestMapping(method = RequestMethod.GET, path = "/tasks")
    @GetMapping(value = "/tasks", params = {"!sort", "!page", "!size"})
    ResponseEntity<?> readAllTasks() {
        logger.warn("Displaying all tasks!");
        return ResponseEntity.ok(repository.findAll());
    }
}

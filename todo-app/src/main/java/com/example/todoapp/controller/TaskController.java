package com.example.todoapp.controller;

import com.example.todoapp.model.Task;
import com.example.todoapp.model.TaskRepository;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskRepository.class);
    @NonNull private final TaskRepository repository;

    //@RequestMapping(method = RequestMethod.GET, path = "/tasks")
    @GetMapping(value = "/tasks", params = {"!sort", "!page", "!size"})
    ResponseEntity<List<Task>> readAllTasks() {
        logger.warn("Displaying all tasks!");
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/tasks")
    ResponseEntity<List<Task>> readAllTasks(Pageable page) {
        logger.info("Displaying custom pageable!");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }

    @GetMapping("tasks/{id}")
    ResponseEntity<Task> readTask(@PathVariable int id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/tasks")
    ResponseEntity<Task> createTask(@Valid @RequestBody Task toCreate) {
        logger.info("Successfully added new task");
        Task newTask = repository.save(toCreate);
        return ResponseEntity.created(URI.create("/" + newTask.getId())).body(newTask);
    }

    @PutMapping("/tasks/{id}")
    ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody @Valid Task toUpdate) {
        if(!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.findById(id).
                ifPresent(task -> {
                    task.updateFrom(toUpdate);
                    repository.save(task);
                });//zamiast @Transactional
        return ResponseEntity.noContent().build();
    }

    @Transactional// Spring creates a proxy that implements the same interface(s) as the class you're annotating, metoda musi być w beanie i publiczna
    //po wywołaniu metody task się zmieni i zostanie to zacommitowane do bazy danych
    //transakcja sie nie wykona gdy poleci wyjątek
    @PatchMapping("/tasks/{id}")
    public ResponseEntity<?> toggleTask(@PathVariable int id) {
        if(!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.findById(id).
                ifPresent(task -> task.setDone(!task.isDone()));
        return ResponseEntity.noContent().build();
    }
}

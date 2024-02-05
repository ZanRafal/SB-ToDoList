package com.example.todoapp.controller;

import com.example.todoapp.model.Task;
import com.example.todoapp.model.TaskRepository;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskRepository.class);
    private final ApplicationEventPublisher eventPublisher;
    @NonNull private final TaskRepository repository;

    //@RequestMapping(method = RequestMethod.GET, path = "/tasks")
    @GetMapping(params = {"!sort", "!page", "!size"})
    ResponseEntity<List<Task>> readAllTasks() {
        logger.warn("Displaying all tasks!");
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping()
    ResponseEntity<List<Task>> readAllTasks(Pageable page) {
        logger.info("Displaying custom pageable!");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }

    @GetMapping("/search/done")
    ResponseEntity<List<Task>> readDoneTasks(@RequestParam(defaultValue = "true") boolean state) {
        return ResponseEntity.ok(repository.findByDone(state));
    }

//    @GetMapping("search/today")
//    ResponseEntity<List<Task>> readTodayTasks() {
//        var deadline = LocalDate.now().plusDays(1).atStartOfDay();
//        return ResponseEntity.ok(repository.findAllByDoneIsFalseAndDeadlineIsNullOrDeadlineIsBeforeOrEqual(deadline));
//    }

    @GetMapping("/{id}")
    ResponseEntity<Task> readTask(@PathVariable int id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    ResponseEntity<Task> createTask(@Valid @RequestBody Task toCreate) {
        logger.info("Successfully added new task");
        Task newTask = repository.save(toCreate);
        return ResponseEntity.created(URI.create("/" + newTask.getId())).body(newTask);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody @Valid Task toUpdate) {
        if(!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.findById(id).
                ifPresent(task -> {
                    task.updateFrom(toUpdate);
                    repository.save(task);
                });
        return ResponseEntity.noContent().build();
    }

    @Transactional// Spring creates a proxy that implements the same interface(s) as the class you're annotating, metoda musi być w beanie i publiczna
    //po wywołaniu metody task się zmieni i zostanie to zacommitowane do bazy danych
    //transakcja sie nie wykona gdy poleci wyjątek
    @PatchMapping("/{id}")
    public ResponseEntity<?> toggleTask(@PathVariable int id) {
        if(!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.findById(id)
                .map(Task::toggle)
                .ifPresent(eventPublisher::publishEvent);
        return ResponseEntity.noContent().build();
    }
}

package com.example.todoapp.controller;

import com.example.todoapp.model.Task;
import com.example.todoapp.model.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TaskRepository repository;

    @Test
    void httpGet_returnsAllTasks() {
        var initialSize = repository.findAll().size();
        repository.save(new Task("foo", LocalDateTime.now()));
        repository.save(new Task("bar", LocalDateTime.now()));

        Task[] result = restTemplate.getForObject("http://localhost:" + port + "/tasks", Task[].class);

        System.out.println(Arrays.toString(result));
        assertThat(result).hasSize(initialSize + 2);
    }

    @Test
    void httpGet_returnsGivenTask() {
        repository.save(new Task("foo", LocalDateTime.now()));

        Task result = restTemplate.getForObject("http://localhost:" + port + "/tasks/" + 1, Task.class);

        assertThat(result).isNotNull();
        assertThat(result.getDescription()).isEqualTo("foo");
        assertThat(result.isDone()).isFalse();
        assertThat(result.getDeadline()).isBefore(LocalDateTime.now());
    }

    @Test
    void httpPost_createsNewTask() {
        var initialSize = repository.findAll().size();
        Task task = new Task("foo", LocalDateTime.now());

        Task result = restTemplate.postForObject("http://localhost:" + port + "/tasks", task, Task.class);

        assertThat(initialSize + 1).isEqualTo(repository.findAll().size());
        assertThat(result).isNotNull();
        assertThat(result.getDescription()).isEqualTo("foo");
        assertThat(result.isDone()).isFalse();
        assertThat(result.getDeadline()).isBefore(LocalDateTime.now());
    }
}
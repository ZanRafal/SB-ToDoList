package com.example.todoapp.logic;

import com.example.todoapp.TaskConfigurationProperties;
import com.example.todoapp.model.*;
import com.example.todoapp.model.projection.GroupReadModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository repository;
    private final TaskGroupRepository taskGroupRepository;
    private final TaskConfigurationProperties config;

    public ProjectService(ProjectRepository repository, TaskGroupRepository taskGroupRepository, TaskConfigurationProperties config) {
        this.repository = repository;
        this.taskGroupRepository = taskGroupRepository;
        this.config = config;
    }

    public List<Project> readAll() {
        return repository.findAll();
    }

    public Project save(final Project toSave) {
        return repository.save(toSave);
    }

    public GroupReadModel createGroup(LocalDateTime deadline, int projectId) {
        if(!config.getTemplate().isAllowMultipleTasks() && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)) {
            throw new IllegalStateException("Only one undone group from project is allowed");
        }

        TaskGroup taskGroup = repository.findById(projectId)
                .map(project -> {
                    var result = new TaskGroup();
                    result.setDescription(project.getDescription());
                    result.setTasks(getTasksFromProject(deadline, project));
                    result.setProject(project);
                    return taskGroupRepository.save(result);
                }).orElseThrow(() -> new IllegalArgumentException("Project with given id not found"));
        return new GroupReadModel(taskGroup);
    }

    private Set<Task> getTasksFromProject(LocalDateTime deadline, Project project) {
        return project.getSteps().stream()
                .map(step -> new Task(
                        step.getDescription(),
                        deadline.plusDays(step.getDaysToDeadline())))
                .collect(Collectors.toSet());
    }
}

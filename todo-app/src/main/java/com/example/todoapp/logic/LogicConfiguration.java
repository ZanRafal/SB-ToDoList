package com.example.todoapp.logic;

import com.example.todoapp.TaskConfigurationProperties;
import com.example.todoapp.model.ProjectRepository;
import com.example.todoapp.model.TaskGroupRepository;
import com.example.todoapp.model.TaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogicConfiguration {

    @Bean
    ProjectService projectService(
            final ProjectRepository projectRepository,
            final TaskGroupRepository taskGroupRepository,
            final TaskConfigurationProperties config) {
        return new ProjectService(projectRepository, taskGroupRepository, config);
    }

    @Bean
    TaskGroupService taskGroupService (
            final TaskGroupRepository repository,
            final TaskRepository taskRepository
    ) {
        return new TaskGroupService(repository, taskRepository);
    }
}

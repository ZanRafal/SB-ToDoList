package com.example.todoapp;

import com.example.todoapp.model.Task;
import com.example.todoapp.model.TaskGroup;
import com.example.todoapp.model.TaskGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class Warmup implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(Warmup.class);

    private final TaskGroupRepository taskGroupRepository;

    Warmup(final TaskGroupRepository taskGroupRepository) {
        this.taskGroupRepository = taskGroupRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.info("Application warmup after context refresh");
        final String description = "ApplicationContextEvent";
        if(!taskGroupRepository.existsByDescription(description)) {
            logger.info("No required element found! Adding it!");
            var group = new TaskGroup();
            group.setDescription(description);
            group.setTasks(Set.of(
                    new Task("ContextClosedEvent", null),
                    new Task("ContextRefreshedEvent", null),
                    new Task("ContextStoppedEvent", null),
                    new Task("ContextStartedEvent", null)
            ));
            taskGroupRepository.save(group);
        }
    }
}

package com.example.todoapp.reports;

import com.example.todoapp.model.event.TaskDone;
import com.example.todoapp.model.event.TaskUndone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ChangedTaskEventListener {
    private static final Logger logger = LoggerFactory.getLogger(ChangedTaskEventListener.class);

    @EventListener
    void on(TaskDone taskDone) {
        logger.info("Got : " + taskDone.toString());
    }

    @EventListener
    void on(TaskUndone taskDone) {
        logger.info("Got : " + taskDone.toString());
    }
}

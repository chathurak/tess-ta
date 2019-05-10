package com.languagematters.tessta.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class AsynchronousServices {

    private final TaskExecutor taskExecutor;

    @Autowired
    public AsynchronousServices(final TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public void executeProcessAsynchronously(Runnable runnable) {
        taskExecutor.execute(() -> taskExecutor.execute(runnable));
    }

}
package com.languagematters.tessta.web.service;

import com.languagematters.tessta.web.task.ProcessTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class AsynchronousServices {
    @Autowired
    private TaskExecutor taskExecutor;

    public void executeProcessAsynchronously(ProcessTask processTask) {
        taskExecutor.execute(() -> taskExecutor.execute(processTask));
    }

}
package com.languagematters.tessta.web.service;

import com.languagematters.tessta.web.task.OcrTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

@Service
public class AsynchronousServices {

    private WebApplicationContext context;
    private final TaskExecutor taskExecutor;

    @Autowired
    public AsynchronousServices(final WebApplicationContext context, final TaskExecutor taskExecutor) {
        this.context = context;
        this.taskExecutor = taskExecutor;
    }

    public void executeOcrTask(String fileId, String taskId, String username, String originalFileName) {
        OcrTask ocrTask = (OcrTask) context.getBean("ocrTask");
        ocrTask.setFileId(fileId);
        ocrTask.setTaskId(taskId);
        ocrTask.setUsername(username);
        ocrTask.setOriginalFileName(originalFileName);

        taskExecutor.execute(ocrTask::process);
    }

}
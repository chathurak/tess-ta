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

    public void executeOcrTask(int documentId, String taskId, String username, String accessToken, String originalFileName) {
        OcrTask ocrTask = (OcrTask) context.getBean("ocrTask");
        ocrTask.setDocumentId(documentId);
        ocrTask.setTaskId(taskId);
        ocrTask.setUsername(username);
        ocrTask.setAccessToken(accessToken);
        ocrTask.setOriginalFileName(originalFileName);

        taskExecutor.execute(ocrTask::process);
    }

}
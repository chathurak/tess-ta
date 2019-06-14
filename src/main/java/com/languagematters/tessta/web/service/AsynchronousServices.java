package com.languagematters.tessta.web.service;

import com.languagematters.tessta.web.task.OcrTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

@Service
public class AsynchronousServices {

    private final TaskExecutor taskExecutor;
    private final WebApplicationContext webApplicationContext;

    @Autowired
    public AsynchronousServices(final WebApplicationContext webApplicationContext, final TaskExecutor taskExecutor) {
        this.webApplicationContext = webApplicationContext;
        this.taskExecutor = taskExecutor;
    }

    public void executeOcrTask(int documentId, String taskId, String username, String accessToken, String originalFileName) {
        OcrTask ocrTask = (OcrTask) webApplicationContext.getBean("ocrTask");
        ocrTask.setDocumentId(documentId);
        ocrTask.setTaskId(taskId);
        ocrTask.setUsername(username);
        ocrTask.setAccessToken(accessToken);
        ocrTask.setOriginalFileName(originalFileName);

        taskExecutor.execute(ocrTask::process);
    }

}
package com.languagematters.tessta.service;

import com.languagematters.tessta.task.OcrTask;
import lombok.RequiredArgsConstructor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

@Service
@RequiredArgsConstructor
public class AsynchronousServices {

    private final TaskExecutor taskExecutor;
    private final WebApplicationContext webApplicationContext;

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
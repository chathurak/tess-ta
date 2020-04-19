package com.languagematters.tessta.controller;

import com.languagematters.tessta.library.model.Task;
import com.languagematters.tessta.library.model.UserFile;
import com.languagematters.tessta.library.services.DocumentServices;
import com.languagematters.tessta.library.services.TaskServices;
import com.languagematters.tessta.security.CurrentUser;
import com.languagematters.tessta.security.UserPrincipal;
import com.languagematters.tessta.service.AsynchronousServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final DocumentServices documentServices;
    private final TaskServices taskServices;
    private final AsynchronousServices asynchronousServices;

    public TaskController(DocumentServices documentServices, TaskServices taskServices, AsynchronousServices asynchronousServices) {
        this.documentServices = documentServices;
        this.taskServices = taskServices;
        this.asynchronousServices = asynchronousServices;
    }

    @GetMapping
    public List<Task> getTasks(@RequestParam(value = "documentId") int documentId) {
        return taskServices.getTasks(documentId);
    }

    @GetMapping("/{taskId}")
    public Task getTask(@PathVariable int taskId) {
        return taskServices.getTask(taskId);
    }

    @PostMapping("/schedule")
    public ResponseEntity<String> process(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody ScheduleTaskRequest scheduleTaskRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String taskId = LocalDateTime.now().format(formatter);

        UserFile userFile = this.documentServices.getDocument(scheduleTaskRequest.getDocumentId());

        Task task = new Task();
        task.setName(taskId); // TODO: Set custom name for the task
        task.setDocumentId(scheduleTaskRequest.getDocumentId());
        task.setTessdataId(1);

        // TODO : Format response
        try {
            taskServices.createTask(task); // Store task entry in db

            asynchronousServices.executeOcrTask(scheduleTaskRequest.getDocumentId(), taskId, currentUser.getEmail(), scheduleTaskRequest.getAccessToken(), userFile.getOriginalFileName());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("XXXX");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body("YYYY");
        }
    }

}

class ScheduleTaskRequest {

    private String accessToken;
    private int documentId;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }
}

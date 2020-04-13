package com.languagematters.tessta.controller;

import com.languagematters.tessta.library.model.Task;
import com.languagematters.tessta.library.model.UserFile;
import com.languagematters.tessta.library.services.DocumentServices;
import com.languagematters.tessta.library.services.TaskServices;
import com.languagematters.tessta.service.AsynchronousServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static com.languagematters.tessta.Temp.USERNAME;
import static com.languagematters.tessta.Temp.USER_ACCESS_TOKEN;

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
    public ResponseEntity<String> process(@Valid @RequestBody ScheduleTaskRequest scheduleTaskRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String taskId = LocalDateTime.now().format(formatter);

        Date timestamp = new Date();

        UserFile userFile = this.documentServices.getDocument(scheduleTaskRequest.getDocumentId());

        Task task = new Task();
        task.setName(taskId); // TODO: Set custom name for the task
        task.setDocumentId(scheduleTaskRequest.getDocumentId());
        task.setTessdataId(1);

        // TODO : Format response
        try {
            taskServices.createTask(task); // Store task entry in db

            asynchronousServices.executeOcrTask(scheduleTaskRequest.getDocumentId(), taskId, USERNAME, USER_ACCESS_TOKEN, userFile.getOriginalFileName());

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

    private int documentId;

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }
}

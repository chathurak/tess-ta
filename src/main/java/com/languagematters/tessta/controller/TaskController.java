package com.languagematters.tessta.controller;

import com.languagematters.tessta.library.model.Task;
import com.languagematters.tessta.library.model.UserFile;
import com.languagematters.tessta.library.services.DocumentServices;
import com.languagematters.tessta.library.services.TaskServices;
import com.languagematters.tessta.security.CurrentUser;
import com.languagematters.tessta.security.UserPrincipal;
import com.languagematters.tessta.service.AsynchronousServices;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {

    private final DocumentServices documentServices;
    private final TaskServices taskServices;
    private final AsynchronousServices asynchronousServices;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<Task> getTasks(@RequestParam(value = "documentId") int documentId) {
        return taskServices.getTasks(documentId);
    }

    @GetMapping("/{taskId}")
    @PreAuthorize("hasRole('USER')")
    public Task getTask(@PathVariable int taskId) {
        return taskServices.getTask(taskId);
    }

    @PostMapping("/schedule")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> process(@CurrentUser UserPrincipal currentUser,
                                          @Valid @RequestBody ScheduleTaskRequest scheduleTaskRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String taskId = LocalDateTime.now().format(formatter);

        Date timestamp = new Date();

        UserFile userFile = this.documentServices.getDocument(scheduleTaskRequest.getDocumentId());

        Task task = new Task();
        task.setName(taskId); // TODO: Set custom name for the task
        task.setDocumentId(scheduleTaskRequest.getDocumentId());
        task.setTessdataId(1);
        task.setCreatedAt(timestamp);
        task.setUpdatedAt(timestamp);

        // TODO : Format response
        try {
            taskServices.createTask(task); // Store task entry in db

            asynchronousServices.executeOcrTask(scheduleTaskRequest.getDocumentId(), taskId, currentUser.getUsername(), currentUser.getAccessToken(), userFile.getOriginalFileName());

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

@Getter
@Setter
class ScheduleTaskRequest {

    private int documentId;

}

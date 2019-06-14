package com.languagematters.tessta.web.controller;

import com.languagematters.tessta.library.model.Task;
import com.languagematters.tessta.library.model.UserFile;
import com.languagematters.tessta.library.services.DocumentServices;
import com.languagematters.tessta.library.services.TaskServices;
import com.languagematters.tessta.web.security.CurrentUser;
import com.languagematters.tessta.web.security.UserPrincipal;
import com.languagematters.tessta.web.service.AsynchronousServices;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TaskController {

    private final DocumentServices documentServices;
    private final TaskServices taskServices;
    private final AsynchronousServices asynchronousServices;

    @Autowired
    public TaskController(final DocumentServices documentServices, final TaskServices taskServices, final AsynchronousServices asynchronousServices) {
        this.documentServices = documentServices;
        this.taskServices = taskServices;
        this.asynchronousServices = asynchronousServices;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<Task> getTasks(@RequestParam(value = "documentId") int documentId) {
        return taskServices.getTasks(documentId);
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
        task.setDocumentId(scheduleTaskRequest.getDocumentId());
        task.setTessdataId(1);
        task.setCreatedAt(timestamp);
        task.setUpdatedAt(timestamp);

        // TODO : Format response
        try {
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

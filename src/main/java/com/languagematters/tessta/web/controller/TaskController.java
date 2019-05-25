package com.languagematters.tessta.web.controller;

import com.languagematters.tessta.web.security.CurrentUser;
import com.languagematters.tessta.web.security.UserPrincipal;
import com.languagematters.tessta.web.service.AsynchronousServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final AsynchronousServices asynchronousServices;

    @Autowired
    public TaskController(final AsynchronousServices asynchronousServices) {
        this.asynchronousServices = asynchronousServices;
    }

    @PostMapping("/run")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> process(@CurrentUser UserPrincipal currentUser,
                                          @RequestParam(value = "fileId") String fileId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String taskId = LocalDateTime.now().format(formatter);

        // TODO : Change
        String originalFileName = "cat";

        try {
            asynchronousServices.executeOcrTask(fileId, taskId, currentUser.getUsername(), originalFileName);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("XXXX");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body("YYYY");
        }
    }

}

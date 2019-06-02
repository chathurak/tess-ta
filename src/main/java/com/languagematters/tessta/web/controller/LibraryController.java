package com.languagematters.tessta.web.controller;

import com.languagematters.tessta.library.model.Task;
import com.languagematters.tessta.library.model.UserFile;
import com.languagematters.tessta.library.services.TaskServices;
import com.languagematters.tessta.library.services.UserFileServices;
import com.languagematters.tessta.web.security.CurrentUser;
import com.languagematters.tessta.web.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library")
public class LibraryController {

    private final UserFileServices userFileServices;
    private final TaskServices taskServices;

    @Autowired
    public LibraryController(final UserFileServices userFileServices, final TaskServices taskServices) {
        this.userFileServices = userFileServices;
        this.taskServices = taskServices;
    }

    @GetMapping("/documents")
    @PreAuthorize("hasRole('USER')")
    public List<UserFile> getUserFiles(@CurrentUser UserPrincipal currentUser) {
        return userFileServices.getUserFiles(currentUser.getId());
    }

    @GetMapping("/tasks")
    @PreAuthorize("hasRole('USER')")
    public List<Task> getTasks(@RequestParam(value = "documentId") int documentId) {
        return taskServices.getTasks(documentId);
    }

}

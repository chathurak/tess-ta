package com.languagematters.tessta.web.controller;

import com.languagematters.tessta.library.model.Task;
import com.languagematters.tessta.library.model.UserFile;
import com.languagematters.tessta.library.services.TaskServices;
import com.languagematters.tessta.library.services.UserFileServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LibraryController {

    private final UserFileServices userFileServices;
    private final TaskServices taskServices;

    @Autowired
    public LibraryController(final UserFileServices userFileServices, final TaskServices taskServices) {
        this.userFileServices = userFileServices;
        this.taskServices = taskServices;
    }

    @RequestMapping(value = "/api/library/userfiles", method = RequestMethod.GET)
    public List<UserFile> getUserFiles() {
        return userFileServices.getUserFiles();
    }

    @RequestMapping(value = "/api/library/tasks", method = RequestMethod.GET)
    public List<Task> getTasks(@RequestParam(value = "userfilename") String userFileName) {
        return taskServices.getTasks(userFileName);
    }

}

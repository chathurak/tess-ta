package com.languagematters.tessta.web.controller;

import com.languagematters.tessta.web.service.AsynchronousServices;
import com.languagematters.tessta.web.task.ProcessTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcessController {

    private final AsynchronousServices asynchronousServices;

    @Autowired
    public ProcessController(final AsynchronousServices asynchronousServices) {
        this.asynchronousServices = asynchronousServices;
    }

    @RequestMapping(value = "/api/process", method = RequestMethod.POST)
    public void process(@RequestParam(value = "pid") String pid,
                        @RequestParam(value = "originalFileName") String originalFileName) {

        ProcessTask processTask = new ProcessTask();
        processTask.setPid(pid);
        processTask.setOriginalFileName(originalFileName);

        asynchronousServices.executeProcessAsynchronously(processTask);
    }

}

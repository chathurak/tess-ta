package com.languagematters.tessta.web.controller;

import com.languagematters.tessta.web.security.CurrentUser;
import com.languagematters.tessta.web.security.UserPrincipal;
import com.languagematters.tessta.web.service.AsynchronousServices;
import com.languagematters.tessta.web.service.StorageServices;
import com.languagematters.tessta.web.task.ProcessTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/file")
public class FileController {

    private final StorageServices storageServices;
    private final AsynchronousServices asynchronousServices;

    @Autowired
    public FileController(final StorageServices storageServices, final AsynchronousServices asynchronousServices) {
        this.storageServices = storageServices;
        this.asynchronousServices = asynchronousServices;
    }

    @PostMapping("/process")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> process(@RequestParam("filepond") MultipartFile file,
                                          @CurrentUser UserPrincipal currentUser) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String pid = now.format(formatter);

        try {
            storageServices.store(file, String.format("%s/%s/", currentUser.getUsername(), pid));

            // TODO : Delete this processing part
            ProcessTask processTask = new ProcessTask();
            processTask.setPid(pid);
            processTask.setUsername(currentUser.getUsername());
            processTask.setOriginalFileName(file.getOriginalFilename());

            asynchronousServices.executeProcessAsynchronously(processTask);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(pid);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(String.format("FAIL to upload %s!", file.getOriginalFilename()));
        }
    }

    @DeleteMapping("/revert")
    public ResponseEntity<String> revert(@RequestParam("file") MultipartFile file) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/load")
    public ResponseEntity<String> load(@RequestParam("file") MultipartFile file) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/restore")
    public ResponseEntity<String> restore(@RequestParam("file") MultipartFile file) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/fetch")
    public ResponseEntity<String> fetch(@RequestParam("file") MultipartFile file) {
        throw new UnsupportedOperationException();
    }

}
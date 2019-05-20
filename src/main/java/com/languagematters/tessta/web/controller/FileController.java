package com.languagematters.tessta.web.controller;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.languagematters.tessta.web.security.CurrentUser;
import com.languagematters.tessta.web.security.UserPrincipal;
import com.languagematters.tessta.web.service.AsynchronousServices;
import com.languagematters.tessta.web.service.StorageServices;
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String fileId = Hashing.sha512().newHasher().putString(file.getOriginalFilename(), Charsets.UTF_8).hash().toString();
        String taskId = LocalDateTime.now().format(formatter);

        try {
            storageServices.store(file, String.format("%s/%s/%s/", currentUser.getUsername(), fileId, taskId));

            // TODO : Delete this processing part
            asynchronousServices.executeOcrTask(fileId, taskId, currentUser.getUsername(), file.getOriginalFilename());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(fileId);
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
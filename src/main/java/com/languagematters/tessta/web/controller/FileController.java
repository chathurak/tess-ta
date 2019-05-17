package com.languagematters.tessta.web.controller;

import com.languagematters.tessta.web.service.StorageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/file")
public class FileController {

    private final StorageServices storageServices;

    @Autowired
    public FileController(final StorageServices storageServices) {
        this.storageServices = storageServices;
    }

    @Deprecated
    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,
                                                   @RequestParam(value = "pid") String pid) {
        try {
            storageServices.store(file, pid);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(String.format("You successfully uploaded %s!", file.getOriginalFilename()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(String.format("FAIL to upload %s!", file.getOriginalFilename()));
        }
    }

    @PostMapping("/process")
    public ResponseEntity<String> process(@RequestParam("filepond") MultipartFile file) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String pid = now.format(formatter);

        try {
            storageServices.store(file, pid);

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
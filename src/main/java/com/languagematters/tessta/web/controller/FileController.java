package com.languagematters.tessta.web.controller;

import com.languagematters.tessta.web.service.StorageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

}
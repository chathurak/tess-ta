package com.languagematters.tessta.controller;

import com.languagematters.tessta.library.model.UserFile;
import com.languagematters.tessta.library.services.DocumentServices;
import com.languagematters.tessta.security.CurrentUser;
import com.languagematters.tessta.security.UserPrincipal;
import com.languagematters.tessta.service.StorageServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/file")
public class FileController {

    private final StorageServices storageServices;
    private final DocumentServices documentServices;

    public FileController(StorageServices storageServices, DocumentServices documentServices) {
        this.storageServices = storageServices;
        this.documentServices = documentServices;
    }

    @PostMapping("/process")
    public ResponseEntity<String> process(@CurrentUser UserPrincipal currentUser, @RequestParam("filepond") MultipartFile file) {
        try {
            UserFile userFile = new UserFile();
            userFile.setUserId(currentUser.getId());
            userFile.setName(file.getOriginalFilename());
            userFile.setOriginalFileName(file.getOriginalFilename());
            int fileId = documentServices.createDocument(userFile);

            storageServices.store(file, String.format("%s/%d/", currentUser.getEmail(), fileId));

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(String.valueOf(fileId));
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
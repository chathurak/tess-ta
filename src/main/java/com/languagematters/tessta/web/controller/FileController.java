package com.languagematters.tessta.web.controller;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.languagematters.tessta.library.model.UserFile;
import com.languagematters.tessta.library.services.UserFileServices;
import com.languagematters.tessta.web.security.CurrentUser;
import com.languagematters.tessta.web.security.UserPrincipal;
import com.languagematters.tessta.web.service.StorageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@RestController
@RequestMapping("/api/file")
public class FileController {

    private final StorageServices storageServices;
    private final UserFileServices userFileServices;

    @Autowired
    public FileController(final StorageServices storageServices, final UserFileServices userFileServices) {
        this.storageServices = storageServices;
        this.userFileServices = userFileServices;
    }

    @PostMapping("/process")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> process(@CurrentUser UserPrincipal currentUser,
                                          @RequestParam("filepond") MultipartFile file) {
        Date timestamp = new Date();

        try {
            UserFile userFile = new UserFile();
            userFile.setUserId(currentUser.getId());
            userFile.setName(file.getOriginalFilename());
            userFile.setCreatedAt(timestamp);
            userFile.setUpdatedAt(timestamp);
            int fileId = userFileServices.createUserFile(userFile);

            storageServices.store(file, String.format("%s/%d/", currentUser.getUsername(), fileId));

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
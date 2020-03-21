package com.languagematters.tessta.controller;

import com.languagematters.tessta.grammar.util.DBUtils;
import com.languagematters.tessta.library.model.UserFile;
import com.languagematters.tessta.library.services.DocumentServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.languagematters.tessta.Temp.USER_ID;

@RestController
@RequestMapping("/api/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentServices documentServices;

    @Autowired
    private DBUtils dbUtils;

    @GetMapping
    public List<UserFile> getUserFiles() {
        return documentServices.getDocuments(USER_ID);
    }

    @DeleteMapping
    public int deleteDocument(@RequestParam("documentId") int documentId) {
        return documentServices.deleteDocument(documentId);
    }

    @PutMapping
    public int renameDocument(@RequestParam("documentId") int documentId,
                              @RequestParam("newName") String newName) {
        return documentServices.renameDocument(documentId, newName);
    }
}

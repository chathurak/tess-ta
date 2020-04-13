package com.languagematters.tessta.controller;

import com.languagematters.tessta.grammar.util.DBUtils;
import com.languagematters.tessta.library.model.UserFile;
import com.languagematters.tessta.library.services.DocumentServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.languagematters.tessta.Temp.USER_ID;

@RestController
@RequestMapping("/api/document")
public class DocumentController {

    private final DocumentServices documentServices;
    private final DBUtils dbUtils;

    public DocumentController(DocumentServices documentServices, DBUtils dbUtils) {
        this.documentServices = documentServices;
        this.dbUtils = dbUtils;
    }

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

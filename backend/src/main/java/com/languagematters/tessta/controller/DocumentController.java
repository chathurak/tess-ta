package com.languagematters.tessta.controller;

import com.languagematters.tessta.grammar.util.DBUtils;
import com.languagematters.tessta.library.model.UserFile;
import com.languagematters.tessta.library.services.DocumentServices;
import com.languagematters.tessta.security.CurrentUser;
import com.languagematters.tessta.security.UserPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


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
    public List<UserFile> getUserFiles(@CurrentUser UserPrincipal currentUser) {
        return documentServices.getDocuments(currentUser.getId());
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

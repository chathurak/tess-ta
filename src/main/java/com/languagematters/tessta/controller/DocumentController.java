package com.languagematters.tessta.controller;

import com.languagematters.tessta.grammar.util.DBUtils;
import com.languagematters.tessta.library.model.UserFile;
import com.languagematters.tessta.library.services.DocumentServices;
import com.languagematters.tessta.security.CurrentUser;
import com.languagematters.tessta.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentServices documentServices;

    @Autowired
    private DBUtils dbUtils;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<UserFile> getUserFiles(@CurrentUser UserPrincipal currentUser) {
        return documentServices.getDocuments(currentUser.getId());
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER')")
    public int deleteDocument(@CurrentUser UserPrincipal currentUser, @RequestParam("documentId") int documentId) {
        return documentServices.deleteDocument(documentId);
    }

    @PutMapping
    @PreAuthorize("hasRole('USER')")
    public int renameDocument(@CurrentUser UserPrincipal currentUser,
                              @RequestParam("documentId") int documentId,
                              @RequestParam("newName") String newName) {
        return documentServices.renameDocument(documentId, newName);
    }
}

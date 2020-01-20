package com.languagematters.tessta.controller;

import com.languagematters.tessta.grammar.service.DictionaryService;
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
@RequestMapping("/api/dictionary")
@RequiredArgsConstructor
public class DictionaryController {

    @Autowired
    private final DictionaryService dictionaryService;

    @Autowired
    private DBUtils dbUtils;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<String> getWords(@CurrentUser UserPrincipal currentUser) {
        dictionaryService.load();
        return dictionaryService.getWords();
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public int addWord(@CurrentUser UserPrincipal currentUser, @RequestParam("word") String word) {
        return dictionaryService.addWord(word);
    }

    @PostMapping("/bulk")
    @PreAuthorize("hasRole('USER')")
    public int addWords(@CurrentUser UserPrincipal currentUser, @RequestBody List<String> words) {
        return dictionaryService.addWords(words);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER')")
    public int deleteWord(@CurrentUser UserPrincipal currentUser, @RequestParam("word") String word) {
        return dictionaryService.deleteWord(word);
    }
    
    @PutMapping
    @PreAuthorize("hasRole('USER')")
    public int changeWord(@CurrentUser UserPrincipal currentUser,
                              @RequestParam("oldWord") String oldWord,
                              @RequestParam("newWord") String newWord) {
        return dictionaryService.changeWord(oldWord, newWord);
    }
}

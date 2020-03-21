package com.languagematters.tessta.controller;

import com.languagematters.tessta.grammar.service.DictionaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dictionary")
@RequiredArgsConstructor
public class DictionaryController {

    @Autowired
    private final DictionaryService dictionaryService;

    @GetMapping
    public List<String> getWords() {
        dictionaryService.load();
        return dictionaryService.getWords();
    }

    @PostMapping
    public int addWord(@RequestParam("word") String word) {
        return dictionaryService.addWord(word);
    }

    @PostMapping("/bulk")
    public int addWords(@RequestBody List<String> words) {
        return dictionaryService.addWords(words);
    }

    @DeleteMapping
    public int deleteWord(@RequestParam("word") String word) {
        return dictionaryService.deleteWord(word);
    }

    @PutMapping
    public int changeWord(@RequestParam("oldWord") String oldWord,
                          @RequestParam("newWord") String newWord) {
        return dictionaryService.changeWord(oldWord, newWord);
    }
}

package com.languagematters.tessta.controller;

import com.languagematters.tessta.grammar.service.DictionaryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dictionary")
public class DictionaryController {

    private final DictionaryService dictionaryService;

    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

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

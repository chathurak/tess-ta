package com.languagematters.tessta.controller;

import com.languagematters.tessta.grammar.service.DictionaryService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dictionary")
public class DictionaryController {

    private final DictionaryService dictionaryService;

    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @GetMapping
    public List<String> getWords(@RequestParam("type") String type) {
        dictionaryService.load(type);
        return dictionaryService.getWords();
    }

    @PostMapping
    public int addWord(@RequestParam("word") String word,@RequestParam("type") String type) {
        return dictionaryService.addWord(word,type);
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

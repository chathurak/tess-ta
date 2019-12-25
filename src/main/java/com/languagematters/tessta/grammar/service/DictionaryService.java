package com.languagematters.tessta.grammar.service;


import com.languagematters.tessta.grammar.util.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class DictionaryService {
    @Autowired
    private DBUtils dbUtils;

    private static HashSet<String> words;

    // Load dictionary words
    public void load() {
        words = dbUtils.loadValues("select * from dictionary", "word");
    }

    // Check whether a word contains in the dictionary
    public boolean contains(String word) {
        return words.contains(word.replace(".", "").replace(",", ""));
    }

    // Add new word to the dictionary
    public void addWord(String word) {
        words.add(word);
        // TODO: Update the database
    }

}

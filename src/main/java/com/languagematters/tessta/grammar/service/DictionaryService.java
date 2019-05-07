package com.languagematters.tessta.grammar.service;



import com.languagematters.tessta.grammar.util.DBUtils;

import java.util.HashSet;

public class DictionaryService {
    private static HashSet<String> words;

    static {
        // load(); // Load words // TODO: Set load
    }

    // Load dictionary words
    public static void load() {
        words = DBUtils.loadValues("select * from dictionary", "word");
    }

    // Check whether a word contains in the dictionary
    public static boolean contains(String word) {
        return words.contains(word.replace(".", "").replace(",", ""));
    }

    // Add new word to the dictionary
    public static void addWord(String word) {
        words.add(word);
        // TODO: Update the database
    }

}

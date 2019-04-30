package com.languagematters.tessta.grammar.controllers;


import com.languagematters.tessta.grammar.common.DictionaryService;
import com.languagematters.tessta.grammar.models.WordObj;

import java.util.List;

public class DictionaryChecker {
    // Check words with the dictionary
    public static void check(List<WordObj> doc) {

        for (WordObj wordObj : doc) {
            checkWordInDictionary(wordObj);
            for (WordObj suggestionWord : wordObj.suggestions) {
                checkWordInDictionary(suggestionWord);
            }
        }

    }

    private static void checkWordInDictionary(WordObj word) {
        if (!DictionaryService.contains(word.value)) {
            word.flags.add("NOT_IN_DICTIONARY");
            if (word.level == 0) {
                word.level = 2;
            }
        }
    }
}
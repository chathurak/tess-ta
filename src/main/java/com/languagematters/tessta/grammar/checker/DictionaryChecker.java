package com.languagematters.tessta.grammar.checker;


import com.languagematters.tessta.grammar.model.WordObj;
import com.languagematters.tessta.grammar.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictionaryChecker {
    @Autowired
    private DictionaryService dictionaryService;

    // Check words with the dictionary
    public void check(List<WordObj> doc) {

        for (WordObj wordObj : doc) {
            checkWordInDictionary(wordObj);
            for (WordObj suggestionWord : wordObj.suggestions) {
                checkWordInDictionary(suggestionWord);
            }
        }

    }

    private void checkWordInDictionary(WordObj word) {
        if (!dictionaryService.contains(word.value)) {
            word.flags.add("NOT_IN_DICTIONARY");
            if (word.level == 0) {
                word.level = 2;
            }
        }
    }
}

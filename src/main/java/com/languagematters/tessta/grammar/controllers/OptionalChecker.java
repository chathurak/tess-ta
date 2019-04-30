package com.languagematters.tessta.grammar.controllers;


import com.languagematters.tessta.grammar.common.DictionaryService;
import com.languagematters.tessta.grammar.config.Rules;
import com.languagematters.tessta.grammar.helpers.DocHelper;
import com.languagematters.tessta.grammar.models.LetterObj;
import com.languagematters.tessta.grammar.models.WordObj;

import java.util.ArrayList;
import java.util.List;

public class OptionalChecker {
    // Check for optional characters
    public static void check(List<WordObj> doc) {

        for (WordObj wordObj : doc) {
            List<String[]> optChars = new ArrayList<>();
            List<LetterObj> optLetters = new ArrayList<>();
            WordObj tmp = DocHelper.generateWord(wordObj.value);

            // List opt chars
            for (LetterObj letterObj : tmp.letters) {
                if (Rules.getOptionalRules().keySet().contains(letterObj.value)) {
                    optChars.add(new String[]{letterObj.value, Rules.getOptionalRules().get(letterObj.value)});
                    optLetters.add(letterObj);
                }
            }

            // Check opt words
            for (String[] entry : optChars) {
                for (LetterObj letterObj : optLetters) {
                    if (letterObj.value.equals(entry[0])) {
                        letterObj.value = entry[1];

                        // Add the word if it is in the dictionary
                        String text = tmp.concatLetters();
                        if (DictionaryService.contains(text)) {
                            wordObj.suggestions.add(DocHelper.generateWord(text));
                        }

                    }
                }
            }

        }

    }

}

package com.languagematters.tessta.grammar.checker;


import com.languagematters.tessta.grammar.config.Rules;
import com.languagematters.tessta.grammar.helper.DocHelper;
import com.languagematters.tessta.grammar.model.LetterObj;
import com.languagematters.tessta.grammar.model.WordObj;
import com.languagematters.tessta.grammar.service.DictionaryService;

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
                if (Rules.getOptionalRules().containsKey(letterObj.value)) {
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

package com.languagematters.tessta.grammar.controllers;


import com.languagematters.tessta.grammar.config.Rules;
import com.languagematters.tessta.grammar.models.LetterObj;
import com.languagematters.tessta.grammar.models.WordObj;
import com.languagematters.tessta.grammar.unicode.Sinhala;

import java.util.List;

public class LegitimacyChecker {
    public static void check(List<WordObj> doc) {
        // For each word
        for (WordObj wordObj : doc) {
            if (wordObj.level == -1) {
                continue; // Ignore special words / Newline
            }

            // Check character legitimacy
            for (LetterObj letterObj : wordObj.letters) {
                if (!Rules.getExBlock().contains(letterObj.value)) {
                    letterObj.flags.add("CHARACTER_LEGITIMACY_ERROR");
                    wordObj.level = 1;
                }
            }

            // Check grammar legitimacy
            if (wordObj.letters.size() > 0 && Sinhala.isModifier(wordObj.letters.get(0).value.charAt(0))) {
                wordObj.letters.get(0).flags.add("GRAMMAR_LEGITIMACY_ERROR");
            }

            for (LetterObj letterObj : wordObj.letters) {
                if (wordObj.letters.indexOf(letterObj) > 0 && Sinhala.isVowel(letterObj.value.charAt(0))) {
                    letterObj.flags.add("GRAMMAR_LEGITIMACY_ERROR");
                }
            }
        }

    }

}

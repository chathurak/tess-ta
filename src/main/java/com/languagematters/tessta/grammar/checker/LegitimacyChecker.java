package com.languagematters.tessta.grammar.checker;


import com.languagematters.tessta.grammar.config.Rules;
import com.languagematters.tessta.grammar.config.SinhalaUnicode;
import com.languagematters.tessta.grammar.model.LetterObj;
import com.languagematters.tessta.grammar.model.WordObj;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LegitimacyChecker {
    public void check(List<WordObj> doc) {
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
            if (wordObj.letters.size() > 0 && SinhalaUnicode.isModifier(wordObj.letters.get(0).value.charAt(0))) {
                wordObj.letters.get(0).flags.add("GRAMMAR_LEGITIMACY_ERROR");
            }

            for (LetterObj letterObj : wordObj.letters) {
                if (wordObj.letters.indexOf(letterObj) > 0 && SinhalaUnicode.isVowel(letterObj.value.charAt(0))) {
                    letterObj.flags.add("GRAMMAR_LEGITIMACY_ERROR");
                }
            }
        }

    }

}

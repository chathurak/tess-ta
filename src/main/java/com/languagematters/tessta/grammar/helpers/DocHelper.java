package com.languagematters.tessta.grammar.helpers;


import com.languagematters.tessta.grammar.models.LetterObj;
import com.languagematters.tessta.grammar.models.WordObj;
import com.languagematters.tessta.grammar.unicode.Sinhala;

import java.util.ArrayList;
import java.util.List;


public class DocHelper {

    private static char ZERO_WIDTH_JOINER = '\u200D';

    public static List<WordObj> parseDoc(String text) {
        String[] wList = text.replace("\n", " \n ").split(" ");

        List<WordObj> doc = new ArrayList<>();
        for (String w : wList) {

            // New lines
            if (w.contains("\n")) {
                WordObj newLine = new WordObj("NEW_LINE");
                newLine.letters.add(new LetterObj("\n"));
                newLine.level = -1;
                doc.add(newLine);
            } else {
                // Generate letters in other words
                doc.add(generateWord(w));
            }
        }

        return doc;
    }

    public static WordObj generateWord(String text) {
        // Generate word and letters
        WordObj word = new WordObj(text);
        int start = 0;
        for (int i = 0; i < text.length(); ++i) {
            if (text.charAt(i) == ZERO_WIDTH_JOINER) { // Skip ZERO_WIDTH_JOINER
                continue;
            }

            // Add complete character
            if (i + 1 == text.length() || text.charAt(i + 1) == '\n' || !(Sinhala.isModifier(text.charAt(i + 1)) || text.charAt(i + 1) == ZERO_WIDTH_JOINER)) {
                word.letters.add(new LetterObj(text.substring(start, i + 1)));
                start = i + 1;
            }
        }

        return word;
    }
}

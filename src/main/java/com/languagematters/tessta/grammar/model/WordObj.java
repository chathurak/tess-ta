package com.languagematters.tessta.grammar.model;

import java.util.ArrayList;
import java.util.List;

public class WordObj {
    public String value;

    public List<LetterObj> letters;

    public List<String> flags;

    public int level;

    public List<WordObj> suggestions;


    public WordObj(String value) {
        this.value = value;

        this.letters = new ArrayList<>();
        this.flags = new ArrayList<>();
        this.suggestions = new ArrayList<>();
        this.level = 0; // default
    }

    public String concatLetters() {
        String text = "";
        for (LetterObj letter : this.letters) {
            text += letter.value;
        }
        return text;
    }


}

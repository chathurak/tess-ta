package com.languagematters.tessta.grammar.models;

import java.util.ArrayList;
import java.util.List;

public class LetterObj {
    public String value;

    public List<String> flags;


    public LetterObj(String value) {
        this.value = value;

        this.flags = new ArrayList<>();
    }
}

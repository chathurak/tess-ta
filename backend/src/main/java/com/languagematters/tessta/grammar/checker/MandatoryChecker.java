package com.languagematters.tessta.grammar.checker;


import com.languagematters.tessta.grammar.config.Rules;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class MandatoryChecker {
    // Check mandatory rules
    public String check(String text) {

        // Apply rules
        HashMap<String, String> rules = Rules.getMandatoryRules();
        for (String key : rules.keySet()) {
            if (text.contains(key)) {
                text = text.replace(key, rules.get(key));
            }
        }

        return text;
    }
}

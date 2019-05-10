package com.languagematters.tessta.grammar.config;


import com.languagematters.tessta.grammar.util.DBUtils;

import java.util.HashMap;
import java.util.HashSet;

public class Rules {
    private static HashMap<String, String> mandatoryRules;
    private static HashMap<String, String> optionalRules;
    private static HashSet<String> exBlock;

    public static void load() {
        mandatoryRules = DBUtils.loadKeyVal("select * from mandatory_rule");
        optionalRules = DBUtils.loadKeyVal("select * from optional_rule");
        exBlock = DBUtils.loadValues("select * from exblock", "character");
    }

    public static HashMap<String, String> getMandatoryRules() {
        return mandatoryRules;
    }

    public static HashMap<String, String> getOptionalRules() {
        return optionalRules;
    }

    public static HashSet<String> getExBlock() {
        return exBlock;
    }

}

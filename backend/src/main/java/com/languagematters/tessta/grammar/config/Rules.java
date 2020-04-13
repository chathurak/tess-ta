package com.languagematters.tessta.grammar.config;


import com.languagematters.tessta.grammar.util.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;

@Service
public class Rules {
    @Autowired
    private DBUtils dbUtils;

    private static HashMap<String, String> mandatoryRules;
    private static HashMap<String, String> optionalRules;
    private static HashSet<String> exBlock;

    public void load() {
        mandatoryRules = dbUtils.loadKeyVal("select * from mandatory_rule");
        optionalRules = dbUtils.loadKeyVal("select * from optional_rule");
        exBlock = dbUtils.loadValues("select * from exblock", "character");
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

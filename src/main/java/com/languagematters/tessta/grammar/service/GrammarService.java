package com.languagematters.tessta.grammar.service;

import com.languagematters.tessta.grammar.checker.DictionaryChecker;
import com.languagematters.tessta.grammar.checker.LegitimacyChecker;
import com.languagematters.tessta.grammar.checker.MandatoryChecker;
import com.languagematters.tessta.grammar.checker.OptionalChecker;
import com.languagematters.tessta.grammar.config.Rules;
import com.languagematters.tessta.grammar.helper.DocHelper;
import com.languagematters.tessta.grammar.model.WordObj;
import com.languagematters.tessta.grammar.util.FileUtils;

import java.util.List;

public class GrammarService {

    public static List<WordObj> process(String testDirPath) {
        // Load rules TODO: Set load once
        Rules.load();
        DictionaryService.load();

        // Load text
        String text = FileUtils.loadTextFile(testDirPath + "/output.txt");

        // Mandatory Check
        text = MandatoryChecker.check(text);

        // Parse the document
        List<WordObj> doc = DocHelper.parseDoc(text);

        // Legitimacy Check
        LegitimacyChecker.check(doc);

        // Optional Check
        OptionalChecker.check(doc);

        // Dictionary check
        DictionaryChecker.check(doc);

        return doc;
    }


}

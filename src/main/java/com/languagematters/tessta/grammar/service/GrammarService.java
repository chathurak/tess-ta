package com.languagematters.tessta.grammar.service;

import com.languagematters.tessta.grammar.checker.DictionaryChecker;
import com.languagematters.tessta.grammar.checker.LegitimacyChecker;
import com.languagematters.tessta.grammar.checker.MandatoryChecker;
import com.languagematters.tessta.grammar.checker.OptionalChecker;
import com.languagematters.tessta.grammar.config.Rules;
import com.languagematters.tessta.grammar.helper.DocHelper;
import com.languagematters.tessta.grammar.model.WordObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrammarService {
    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private DictionaryChecker dictionaryChecker;

    @Autowired
    private MandatoryChecker mandatoryChecker;

    @Autowired
    private LegitimacyChecker legitimacyChecker;

    @Autowired
    private OptionalChecker optionalChecker;

    @Autowired
    private Rules rules;

    public List<WordObj> process(String text) {
        // Load rules TODO: Set load once
        rules.load();
        dictionaryService.load();

        // Mandatory Check
        text = mandatoryChecker.check(text);

        // Parse the document
        List<WordObj> doc = DocHelper.parseDoc(text);

        // Legitimacy Check
        legitimacyChecker.check(doc);

        // Optional Check
        optionalChecker.check(doc);

        // Dictionary check
        dictionaryChecker.check(doc);

        return doc;
    }


}

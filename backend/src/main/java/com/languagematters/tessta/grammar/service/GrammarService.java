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
import com.languagematters.tessta.grammar.util.DBUtils;
// import info.debatty.java.stringsimilarity.*;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Iterator;

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

    @Autowired
    private DBUtils dbUtils;

    private final Connection connection;
    private static HashSet<String> words;

    public GrammarService(Connection connection) {
        this.connection = connection;
    }

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

    // public ArrayList<String> getSuggestionWords(String word) {
    //     int i = 0;
    //     Levenshtein l = new Levenshtein();
    //     ArrayList<String> worldlist = new ArrayList<String>();
    //     words = dbUtils.loadValues("select * from dictionary", "word");
    //     Iterator<String> iterator = words.iterator();
    //     while(iterator.hasNext()){
    //         String temp = iterator.next();
    //         if(l.distance(word, temp) < 4){
    //             worldlist.add(temp);
    //             i++;
    //         }
    //     }
    //     return worldlist;
    // }
}
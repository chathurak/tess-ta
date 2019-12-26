package com.languagematters.tessta.grammar.service;


import com.languagematters.tessta.grammar.util.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class DictionaryService {
    @Autowired
    private DBUtils dbUtils;

    private static HashSet<String> words;

    // Load dictionary words
    public void load() {
        words = dbUtils.loadValues("select * from dictionary", "word");
    }

    // Check whether a word contains in the dictionary
    public boolean contains(String word) {
        return words.contains(word.replace(".", "").replace(",", ""));
    }

    // Return all the words in the dictionary
    public List<String> getWords() {
        return new ArrayList<>(words);
    }

    // Delete a word from the dictionary
    public int deleteWord(String word) {
        try {
            PreparedStatement pstmt = dbUtils.getConnection().prepareStatement("delete from dictionary where word = ?");
            pstmt.setString(1, word);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Change a word in the dictionary
    public int changeWord(String oldWord, String newWord) {
        try {
            PreparedStatement pstmt = dbUtils.getConnection().prepareStatement("update dictionary set word = ? where word = ?");
            pstmt.setString(1, newWord);
            pstmt.setString(2, oldWord);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Add new word to the dictionary
    public int addWord(String word) {
        try {
            PreparedStatement pstmt = dbUtils.getConnection().prepareStatement("INSERT INTO dictionary(word) VALUES(?)");
            pstmt.setString(1, word);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

}

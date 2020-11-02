package com.languagematters.tessta.grammar.service;


import com.languagematters.tessta.grammar.util.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class DictionaryService {
    @Autowired
    private DBUtils dbUtils;

    private final Connection connection;

    private static HashSet<String> words;

    public DictionaryService(Connection connection) {
        this.connection = connection;
    }

    // Load dictionary words based on type
    public void load(String type) {
        words = dbUtils.loadValues("select * from dictionary where type = "+type, "word");
    }

    // load all dictionary words
    public void loadAll() {
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
            PreparedStatement pstmt = connection.prepareStatement("DELETE FROM dictionary WHERE word = ?");
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
            PreparedStatement pstmt = connection.prepareStatement("UPDATE dictionary SET word = ? where word = ?");
            pstmt.setString(1, newWord);
            pstmt.setString(2, oldWord);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Add new word to the dictionary
    public int addWord(String word,String type) {
        try {
            type = "2";
            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO dictionary(word,type) VALUES(?,?)");
            pstmt.setString(1, word);
            pstmt.setString(2, type);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Add new words to the dictionary
    public int addWords(List<String> words) {
        try {
            Statement stmt = connection.createStatement();
            String sql = "INSERT INTO dictionary(word) VALUES(\"" + String.join("\"),(\"", words) + "\")";
            System.out.println(sql);
            return stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

}

package com.languagematters.tessta.report.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ConfusionMap {
    // TODO :  Make this more efficient by first reading the whole text and count the unique characters
    // Multidimensional array of integers => Two hash maps

    private HashMap<String, HashMap<String, Integer>> countMap = new HashMap<>();
    private HashMap<String, HashMap<String, ArrayList<String>>> wordMap = new HashMap<>();

    private int outerCount = 0;

    public int getOuterCount() {
        return outerCount;
    }

    public Set<Map.Entry<String, HashMap<String, Integer>>> getCountOuterEntrySet() {
        return countMap.entrySet();
    }

    public Set<Map.Entry<String, Integer>> getCountInnerEntrySet(String outerKey) {
        return countMap.get(outerKey).entrySet();
    }

    public Integer getCountValue(String outerKey, String innerKey) {
        return countMap.get(outerKey).get(innerKey);
    }

    public ArrayList<String> getWordList(String outerKey, String innerKey) {
        return wordMap.get(outerKey).get(innerKey);
    }

    public boolean containsOuterKey(String outerKey) {
        return countMap.containsKey(outerKey);
    }

    public boolean containsInnerKey(String outerKey, String innerKey) {
        return countMap.get(outerKey).containsKey(innerKey);
    }

    public void addOuterKey(String outerKey) {
        countMap.put(outerKey, new HashMap<>());
        wordMap.put(outerKey, new HashMap<>());

        this.outerCount++;
    }

    public void incrementCount(String outerKey, String innerKey) {
        countMap.get(outerKey).put(innerKey, countMap.get(outerKey).getOrDefault(innerKey, 0) + 1);
    }

    public void addWord(String outerKey, String innerKey, String word) {
        wordMap.get(outerKey).computeIfAbsent(innerKey, k -> new ArrayList<>());
        wordMap.get(outerKey).get(innerKey).add(word);
    }
}

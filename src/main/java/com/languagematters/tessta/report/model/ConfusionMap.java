package com.languagematters.tessta.report.model;

import com.languagematters.tessta.grammar.util.TextUtils;
import com.languagematters.tessta.report.google.Operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ConfusionMap {
    // TODO :  Make this more efficient by first reading the whole text and count the unique characters
    // TODO : Two hash maps => Multidimensional array of integers

    private HashMap<String, HashMap<String, Integer>> countMap = new HashMap<>();
    private HashMap<String, HashMap<String, ArrayList<String>>> wordMap = new HashMap<>();

    public ConfusionMap(DiffList diffList) {
        List<CustomDiff> deltas = diffList.getCustomDiffs();

        for (int i = 0; i < deltas.size(); i++) {
            CustomDiff currentDiff = deltas.get(i);

            if (currentDiff.getOperation() == Operation.EQUAL) {    // Add equally matched
                for (String letter : TextUtils.splitLetters(currentDiff.getText())) {
                    if (!countMap.containsKey(letter)) {
                        addInputKey(letter);
                    }

                    incrementCount(letter, letter);
                    addWord(letter, letter, currentDiff.getText());

                    break;
                }
            } else {    // Add unmatched
                try {   // Handle one character change
                    CustomDiff preDiff = deltas.get(i - 1);
                    CustomDiff nextDiff = deltas.get(i + 1);
                    CustomDiff afterNextDiff = deltas.get(i + 2);
                    if (preDiff.getCustomOperation() == CustomOperation.CUSTOM_EQUAL
                            && afterNextDiff.getCustomOperation() == CustomOperation.CUSTOM_EQUAL
                            && nextDiff.getCustomOperation() != CustomOperation.CUSTOM_EQUAL) {
                        List<String> currentLetters = TextUtils.splitLetters(currentDiff.getText());
                        List<String> nextLetters = TextUtils.splitLetters(nextDiff.getText());

                        if (currentLetters.size() == nextLetters.size()) {
                            for (int j = 0; j < currentLetters.size(); j++) {
                                String currentLetter = currentLetters.get(j);
                                String nextLetter = nextLetters.get(j);
                                if (!countMap.containsKey(currentLetter)) {
                                    addInputKey(currentLetter);
                                }

                                incrementCount(currentLetter, nextLetter);
                                addWord(currentLetter, nextLetter, currentDiff.getText());
                            }
                        }
                    }
                } catch (Exception e) {
                    // TODO: 4/22/18 Handle this properly
                    System.out.println(e.toString());
                }
            }
        }
    }

    public Set<String> getInputKeySet() {
        return countMap.keySet();
    }

    public Set<String> getOutputKeySet(String inputKey){
        return countMap.get(inputKey).keySet();
    }

    public boolean containsOutputKey(String inputKey, String outputKey) {
        return countMap.get(inputKey).containsKey(outputKey);
    }

    public Integer getCount(String inputKey, String outputKey) {
        return countMap.get(inputKey).get(outputKey);
    }

    public void addInputKey(String inputKey) {
        countMap.put(inputKey, new HashMap<>());
        wordMap.put(inputKey, new HashMap<>());
    }

    public void incrementCount(String inputKey, String outputKey) {
        countMap.get(inputKey).put(outputKey, countMap.get(inputKey).getOrDefault(outputKey, 0) + 1);
    }

    public void addWord(String inputKey, String outputKey, String word) {
        wordMap.get(inputKey).computeIfAbsent(outputKey, k -> new ArrayList<>());
        wordMap.get(inputKey).get(outputKey).add(word);
    }
}

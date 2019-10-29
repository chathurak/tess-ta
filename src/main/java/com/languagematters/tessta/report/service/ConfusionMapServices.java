package com.languagematters.tessta.report.service;

import com.languagematters.tessta.grammar.util.TextUtils;
import com.languagematters.tessta.report.google.Operation;
import com.languagematters.tessta.report.model.ConfusionMap;
import com.languagematters.tessta.report.model.CustomDiff;
import com.languagematters.tessta.report.model.CustomOperation;
import com.languagematters.tessta.report.model.DiffList;

import java.util.List;

public class ConfusionMapServices {

    public static ConfusionMap getConfusionMap(DiffList diffList) {
        List<CustomDiff> deltas = diffList.getCustomDiffs();
        ConfusionMap confusionMap = new ConfusionMap();

        for (int i = 0; i < deltas.size(); i++) {
            CustomDiff currentDiff = deltas.get(i);

            if (currentDiff.getOperation() == Operation.EQUAL) {    // Add equally matched
                for (String letter : TextUtils.splitLetters(currentDiff.getText())) {
                    if (!confusionMap.containsOuterKey(letter)) {
                        confusionMap.addOuterKey(letter);
                    }

                    confusionMap.incrementCount(letter, letter);
                    confusionMap.addWord(letter, letter, currentDiff.getText());

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
                                if (!confusionMap.containsOuterKey(currentLetter)) {
                                    confusionMap.addOuterKey(currentLetter);
                                }

                                confusionMap.incrementCount(currentLetter, nextLetter);
                                confusionMap.addWord(currentLetter, nextLetter, currentDiff.getText());
                            }
                        }
                    }
                } catch (Exception e) {
                    // TODO: 4/22/18 Handle this properly
                    System.out.println(e.toString());
                }
            }
        }

        return confusionMap;
    }

}

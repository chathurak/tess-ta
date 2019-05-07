package com.languagematters.tessta.report.service;

import com.languagematters.tessta.grammar.util.TextUtils;
import com.languagematters.tessta.ocr.google.DiffMatchPatch;
import com.languagematters.tessta.report.model.ConfusionMap;

import java.util.List;

public class ConfusionMapServices {

    /**
     * @param deltas
     * @return
     */
    public static ConfusionMap getConfusionMap(List<DiffServices.CustomDiff> deltas) {
        ConfusionMap confusionMap = new ConfusionMap();

        for (int i = 0; i < deltas.size(); i++) {
            DiffServices.CustomDiff currentDiff = deltas.get(i);

            if (currentDiff.googleDiffOperation == DiffMatchPatch.Operation.EQUAL) {    // Add equally matched
                for (String letter : TextUtils.splitLetters(currentDiff.text)) {
                    if (!confusionMap.containsOuterKey(letter)) {
                        confusionMap.addOuterKey(letter);
                    }

                    confusionMap.incrementCount(letter, letter);
                    confusionMap.addWord(letter, letter, currentDiff.text);

                    break;
                }
            } else {    // Add unmatched
                try {   // Handle one character change
                    DiffServices.CustomDiff preDiff = deltas.get(i - 1);
                    DiffServices.CustomDiff nextDiff = deltas.get(i + 1);
                    DiffServices.CustomDiff afterNextDiff = deltas.get(i + 2);
                    if (preDiff.customOperation == DiffServices.CustomOperation.CUSTOM_EQUAL
                            && afterNextDiff.customOperation == DiffServices.CustomOperation.CUSTOM_EQUAL
                            && nextDiff.customOperation != DiffServices.CustomOperation.CUSTOM_EQUAL) {
                        List<String> currentLetters = TextUtils.splitLetters(currentDiff.text);
                        List<String> nextLetters = TextUtils.splitLetters(nextDiff.text);

                        if (currentLetters.size() == nextLetters.size()) {
                            for (int j = 0; j < currentLetters.size(); j++) {
                                String currentLetter = currentLetters.get(j);
                                String nextLetter = nextLetters.get(j);
                                if (!confusionMap.containsOuterKey(currentLetter)) {
                                    confusionMap.addOuterKey(currentLetter);
                                }

                                confusionMap.incrementCount(currentLetter, nextLetter);
                                confusionMap.addWord(currentLetter, nextLetter, currentDiff.text);
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

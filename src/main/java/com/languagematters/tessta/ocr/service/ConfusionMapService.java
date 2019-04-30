package com.languagematters.tessta.ocr.service;

import com.languagematters.tessta.grammar.utils.TextUtils;
import com.languagematters.tessta.ocr.google.DiffMatchPatch;
import com.languagematters.tessta.ocr.model.ConfusionMap;

import java.util.List;

public class ConfusionMapService {

    /**
     * @param deltas
     * @return
     */
    public static ConfusionMap getConfusionMap(List<DiffService.CustomDiff> deltas) {
        ConfusionMap confusionMap = new ConfusionMap();

        for (int i = 0; i < deltas.size(); i++) {
            DiffService.CustomDiff currentDiff = deltas.get(i);

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
                    DiffService.CustomDiff preDiff = deltas.get(i - 1);
                    DiffService.CustomDiff nextDiff = deltas.get(i + 1);
                    DiffService.CustomDiff afterNextDiff = deltas.get(i + 2);
                    if (preDiff.customOperation == DiffService.CustomOperation.CUSTOM_EQUAL
                            && afterNextDiff.customOperation == DiffService.CustomOperation.CUSTOM_EQUAL
                            && nextDiff.customOperation != DiffService.CustomOperation.CUSTOM_EQUAL) {
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

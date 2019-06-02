package com.languagematters.tessta.report.service;

import com.languagematters.tessta.report.google.DiffMatchPatch;
import com.languagematters.tessta.report.model.CustomDiff;
import com.languagematters.tessta.report.model.CustomOperation;
import com.languagematters.tessta.report.model.DiffList;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static com.languagematters.tessta.report.google.Operation.*;

public class DiffServices {

    public static DiffList getDefaultDiff(String inputTxtPath, String outputTxtPath) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(inputTxtPath));
        String s1 = new String(encoded, Charset.defaultCharset());

        byte[] encoded2 = Files.readAllBytes(Paths.get(outputTxtPath));
        String s2 = new String(encoded2, Charset.defaultCharset());

        DiffMatchPatch difference = new DiffMatchPatch();
        List<CustomDiff> customDiffs = new ArrayList<>();

        for (DiffMatchPatch.Diff d : difference.diff_main(s2, s1)) {
            CustomDiff diff = new CustomDiff();
            diff.setText(d.text);

            switch (d.operation) {
                case EQUAL:
                    diff.setOperation(EQUAL);
                    diff.setCustomOperation(CustomOperation.CUSTOM_EQUAL);
                    diff.setDescription("Equal");
                    break;
                case INSERT:
                    diff.setOperation(INSERT);
                    diff.setCustomOperation(CustomOperation.CUSTOM_INSERT);
                    diff.setDescription("Insert");
                    break;
                case DELETE:
                    diff.setOperation(DELETE);
                    diff.setCustomOperation(CustomOperation.CUSTOM_DELETE);
                    diff.setDescription("Delete");
                    break;
            }

            customDiffs.add(diff);
        }

        DiffServices.formatDiff(customDiffs);

        DiffList diffList = new DiffList();
        diffList.setCustomDiffs(customDiffs);

        return diffList;
    }

    private static void formatDiff(List<CustomDiff> deltas) {
        ListIterator<CustomDiff> iterator = deltas.listIterator();
        while (iterator.hasNext()) {
            CustomDiff diff = iterator.next();

            if (diff.getText().matches("[ ]+")) {                                      // One or more than one space
                if (diff.getText().equals(" ")) {                                                    // One space
                    if (diff.getOperation() == INSERT) {                             // Insert
                        diff.setCustomOperation(CustomOperation.INSERT_SPACE);
                        diff.setDescription("Insert space");
                    } else if (diff.getOperation() == DELETE) {                      // Delete
                        diff.setCustomOperation(CustomOperation.DELETE_SPACE);
                        diff.setDescription("Delete space");
                    }
                } else {                                                                        // Multiple spaces
                    if (diff.getOperation() == INSERT) {                             // Insert
                        diff.setCustomOperation(CustomOperation.INSERT_SPACES);
                        diff.setDescription("Insert spaces");
                    } else if (diff.getOperation() == DELETE) {                      // Delete
                        diff.setCustomOperation(CustomOperation.DELETE_SPACES);
                        diff.setDescription("Delete spaces");
                    }
                }
            } else if (diff.getText().matches("[\n]+")) {                                // One or more than one new line
                if (diff.getText().equals("\n")) {                                                   // One new line
                    if (diff.getOperation() == INSERT) {                             // Insert
                        diff.setCustomOperation(CustomOperation.INSERT_LINE);
                        diff.setDescription("Insert new line");
                    } else if (diff.getOperation() == DELETE) {                      // Delete
                        diff.setCustomOperation(CustomOperation.DELETE_LINE);
                        diff.setDescription("Delete line");
                    }
                } else {                                                                        // Multiple new lines
                    if (diff.getOperation() == INSERT) {                             // Insert
                        diff.setCustomOperation(CustomOperation.INSERT_LINES);
                        diff.setDescription("Insert new lines");
                    } else if (diff.getOperation() == DELETE) {                      // Delete
                        diff.setCustomOperation(CustomOperation.DELETE_LINES);
                        diff.setDescription("Delete lines");
                    }
                }
            }

        }
    }

}

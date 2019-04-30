package com.languagematters.tessta.ocr.service;

import com.languagematters.tessta.ocr.google.DiffMatchPatch;
import com.languagematters.tessta.ocr.google.DiffMatchPatch.Operation;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static com.languagematters.tessta.ocr.google.DiffMatchPatch.Operation.*;

public class DiffService {

    /**
     * @param outputDirectoryPath
     * @return
     * @throws IOException
     */
    public static List<CustomDiff> getDefaultDiff(String inputTxtPath, String outputTxtPath) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(inputTxtPath));
        String s1 = new String(encoded, Charset.defaultCharset());

        byte[] encoded2 = Files.readAllBytes(Paths.get(outputTxtPath));
        String s2 = new String(encoded2, Charset.defaultCharset());

        DiffMatchPatch difference = new DiffMatchPatch();
        List<CustomDiff> deltas = new ArrayList<>();

        for (DiffMatchPatch.Diff d : difference.diff_main(s2, s1)) {
            CustomDiff diff = new CustomDiff();
            diff.text = d.text;

            switch (d.operation) {
                case EQUAL:
                    diff.googleDiffOperation = EQUAL;
                    diff.customOperation = CustomOperation.CUSTOM_EQUAL;
                    diff.description = "Equal";
                    break;
                case INSERT:
                    diff.googleDiffOperation = INSERT;
                    diff.customOperation = CustomOperation.CUSTOM_INSERT;

                    diff.description = "Insert";
                    break;
                case DELETE:
                    diff.googleDiffOperation = DELETE;
                    diff.customOperation = CustomOperation.CUSTOM_DELETE;
                    diff.description = "Delete";
                    break;
            }

            deltas.add(diff);
        }

        DiffService.formatDiff(deltas);

        return deltas;
    }

    /**
     * @param deltas
     */
    private static void formatDiff(List<CustomDiff> deltas) {
        ListIterator<CustomDiff> iterator = deltas.listIterator();
        while (iterator.hasNext()) {
            CustomDiff diff = iterator.next();

            if (diff.text.matches("[ ]+")) {                                      // One or more than one space
                if (diff.text.equals(" ")) {                                                    // One space
                    if (diff.googleDiffOperation == INSERT) {                             // Insert
                        diff.customOperation = CustomOperation.INSERT_SPACE;
                        diff.description = "Insert space";
                    } else if (diff.googleDiffOperation == DELETE) {                      // Delete
                        diff.customOperation = CustomOperation.DELETE_SPACE;
                        diff.description = "Delete space";
                    }
                } else {                                                                        // Multiple spaces
                    if (diff.googleDiffOperation == INSERT) {                             // Insert
                        diff.customOperation = CustomOperation.INSERT_SPACES;
                        diff.description = "Insert spaces";
                    } else if (diff.googleDiffOperation == DELETE) {                      // Delete
                        diff.customOperation = CustomOperation.DELETE_SPACES;
                        diff.description = "Delete spaces";
                    }
                }
            } else if (diff.text.matches("[\n]+")) {                                // One or more than one new line
                if (diff.text.equals("\n")) {                                                   // One new line
                    if (diff.googleDiffOperation == INSERT) {                             // Insert
                        diff.customOperation = CustomOperation.INSERT_LINE;
                        diff.description = "Insert new line";
                    } else if (diff.googleDiffOperation == DELETE) {                      // Delete
                        diff.customOperation = CustomOperation.DELETE_LINE;
                        diff.description = "Delete line";
                    }
                } else {                                                                        // Multiple new lines
                    if (diff.googleDiffOperation == INSERT) {                             // Insert
                        diff.customOperation = CustomOperation.INSERT_LINES;
                        diff.description = "Insert new lines";
                    } else if (diff.googleDiffOperation == DELETE) {                      // Delete
                        diff.customOperation = CustomOperation.DELETE_LINES;
                        diff.description = "Delete lines";
                    }
                }
            }

        }
    }

    public enum CustomOperation {
        CUSTOM_EQUAL,
        CUSTOM_INSERT,
        CUSTOM_DELETE,

        INSERT_SPACE,
        DELETE_SPACE,

        INSERT_SPACES,
        DELETE_SPACES,

        INSERT_LINE,
        DELETE_LINE,

        INSERT_LINES,
        DELETE_LINES
    }

    public static class CustomDiff {
        public String text;
        public Operation googleDiffOperation;
        public CustomOperation customOperation;
        public String description;
    }
}

package com.languagematters.tessta.report.model.report;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;
import com.languagematters.tessta.grammar.util.DBUtils;
import com.languagematters.tessta.report.model.ConfusionMap;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

public class ConfusionSummaryReport {

    ConfusionMap confusionMap;

    List<List<Object>> rows;

    public ConfusionSummaryReport(ConfusionMap confusionMap) throws IOException, GeneralSecurityException {
        this.confusionMap = confusionMap;

        rows = new ArrayList<>();

        HashSet<String> exBlock = DBUtils.loadValues("select * from exblock", "character");
        LinkedHashSet<String> exBlockLinked = new LinkedHashSet<>(exBlock);

        // Create heading
        rows.add(Arrays.asList("Accuracy percentage", "Error percentage", "Correct count", "Error count", "Total count", "Character", "Error(s)"));

        int totalCorrectCount = 0;
        int totalErrorCount = 0;

        for (String character : exBlockLinked) {
            if (confusionMap.containsOuterKey(character)) {
                List<Object> row = new ArrayList<>();
                int errorCount = 0;
                int correctCount = 0;
                List<String> errorWordList = new ArrayList<>();

                for (Map.Entry<String, Integer> entry : confusionMap.getCountInnerEntrySet(character)) {
                    if (character.equals(entry.getKey())) {
                        correctCount += entry.getValue();
                    } else {
                        errorCount += entry.getValue();
                        errorWordList.add(entry.getKey());
                    }
                }

                row.add(String.format("%.2f%%", 100.0 * correctCount / (correctCount + errorCount)));
                row.add(String.format("%.2f%%", 100.0 * errorCount / (correctCount + errorCount)));
                row.add(correctCount);
                row.add(errorCount);
                row.add(correctCount + errorCount);
                row.add(character);
                row.addAll(errorWordList);

                totalCorrectCount += correctCount;
                totalErrorCount += errorCount;

                rows.add(row);
            }
        }

        // Add summary row
        List<Object> row = new ArrayList<>();
        row.add(String.format("%.2f%%", 100.0 * totalCorrectCount / (totalCorrectCount + totalErrorCount)));
        row.add(String.format("%.2f%%", 100.0 * totalErrorCount / (totalCorrectCount + totalErrorCount)));
        row.add(totalCorrectCount);
        row.add(totalErrorCount);
        row.add(totalCorrectCount + totalErrorCount);

        rows.add(row);
    }

    public void writeReport(Drive drive, Sheets sheets, String parentDirId, String outputFileName) throws IOException {
        // Create spreadsheet
        File spreadsheetMetadata = new File();
        spreadsheetMetadata.setName(outputFileName);
        spreadsheetMetadata.setMimeType("application/vnd.google-apps.spreadsheet");
        spreadsheetMetadata.setParents(Collections.singletonList(parentDirId));
        File spreadsheetFile = drive.files()
                .create(spreadsheetMetadata)
                .setFields("id")
                .execute();

        // Update formatting
        List<GridRange> summaryRange = Collections.singletonList(new GridRange()
                .setSheetId(0)
                .setStartRowIndex(rows.size() - 1)
                .setEndRowIndex(rows.size())
                .setStartColumnIndex(0)
                .setEndColumnIndex(5)
        );
        List<GridRange> charRange = Collections.singletonList(new GridRange()
                .setSheetId(0)
                .setStartRowIndex(1)
                .setEndRowIndex(rows.size() - 2)
                .setStartColumnIndex(5)
                .setEndColumnIndex(6)
        );

        List<Request> requests = Arrays.asList(
                new Request().setUpdateDimensionProperties(
                        new UpdateDimensionPropertiesRequest()
                                .setRange(
                                        new DimensionRange()
                                                .setSheetId(0)
                                                .setDimension("COLUMNS")
                                                .setStartIndex(0)
                                                .setEndIndex(6)
                                )
                                .setProperties(new DimensionProperties().setPixelSize(100))
                                .setFields("pixelSize")
                ),
                new Request().setAddConditionalFormatRule(
                        new AddConditionalFormatRuleRequest()
                                .setRule(new ConditionalFormatRule()
                                        .setRanges(summaryRange)
                                        .setBooleanRule(new BooleanRule()
                                                .setCondition(new BooleanCondition()
                                                        .setType("CUSTOM_FORMULA")
                                                        .setValues(Collections.singletonList(
                                                                new ConditionValue().setUserEnteredValue("=TRUE")
                                                        ))
                                                )
                                                .setFormat(new CellFormat().setBackgroundColor(
                                                        new Color()
                                                                .setRed(0.937f)
                                                                .setGreen(0.603f)
                                                                .setBlue(0.603f)
                                                ))
                                        )
                                )
                                .setIndex(0)
                ),
                new Request().setAddConditionalFormatRule(
                        new AddConditionalFormatRuleRequest()
                                .setRule(new ConditionalFormatRule()
                                        .setRanges(charRange)
                                        .setBooleanRule(new BooleanRule()
                                                .setCondition(new BooleanCondition()
                                                        .setType("CUSTOM_FORMULA")
                                                        .setValues(Collections.singletonList(
                                                                new ConditionValue().setUserEnteredValue("=TRUE")
                                                        ))
                                                )
                                                .setFormat(new CellFormat().setBackgroundColor(
                                                        new Color()
                                                                .setRed(0.647f)
                                                                .setGreen(0.839f)
                                                                .setBlue(0.654f)
                                                ))
                                        )
                                )
                                .setIndex(0)
                )
        );

        BatchUpdateSpreadsheetRequest batchUpdateSpreadsheetRequest = new BatchUpdateSpreadsheetRequest().setRequests(requests);
        BatchUpdateSpreadsheetResponse batchUpdateSpreadsheetResponse = sheets.spreadsheets()
                .batchUpdate(spreadsheetFile.getId(), batchUpdateSpreadsheetRequest)
                .execute();

        // Update values
        List<ValueRange> data = new ArrayList<ValueRange>();
        data.add(new ValueRange().setRange("A1").setValues(rows));

        BatchUpdateValuesRequest batchUpdateValuesRequest = new BatchUpdateValuesRequest()
                .setValueInputOption("RAW")
                .setData(data);
        BatchUpdateValuesResponse batchUpdateValuesResponse = sheets.spreadsheets().values().batchUpdate(spreadsheetFile.getId(), batchUpdateValuesRequest).execute();
    }
}

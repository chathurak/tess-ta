package com.languagematters.tessta.report.type;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.AddConditionalFormatRuleRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetResponse;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesResponse;
import com.google.api.services.sheets.v4.model.BooleanCondition;
import com.google.api.services.sheets.v4.model.BooleanRule;
import com.google.api.services.sheets.v4.model.CellFormat;
import com.google.api.services.sheets.v4.model.Color;
import com.google.api.services.sheets.v4.model.ConditionValue;
import com.google.api.services.sheets.v4.model.ConditionalFormatRule;
import com.google.api.services.sheets.v4.model.DimensionProperties;
import com.google.api.services.sheets.v4.model.DimensionRange;
import com.google.api.services.sheets.v4.model.GridRange;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.UpdateDimensionPropertiesRequest;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.languagematters.tessta.report.model.ConfusionMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class ConfusionReport {

    private final ArrayList<String> inputCharacters;
    private final ArrayList<String> outputCharacters;

    private final List<List<Object>> rows;

    public ConfusionReport(final ConfusionMap confusionMap) {
        rows = new ArrayList<>();

        // Get characters and subCharacters
        HashSet<String> inputCharacterSet = new HashSet<>();
        HashSet<String> outputCharacterSet = new HashSet<>();

        for (String character : confusionMap.getInputKeySet()) {
            inputCharacterSet.add(character);
            outputCharacterSet.addAll(confusionMap.getOutputKeySet(character));
        }

        inputCharacters = new ArrayList<>(inputCharacterSet);
        outputCharacters = new ArrayList<>(inputCharacters);
        outputCharacterSet.removeAll(inputCharacterSet);
        outputCharacters.addAll(outputCharacterSet);

        // Create heading
        List<Object> heading = new ArrayList<>();
        heading.add(" ");
        heading.addAll(outputCharacters);
        rows.add(heading);

        for (String character : inputCharacters) {
            List<Object> row = new ArrayList<>();
            row.add(character);

            for (String subCharacter : outputCharacters) {
                if (confusionMap.containsOutputKey(character, subCharacter)) {
                    row.add(confusionMap.getCount(character, subCharacter));
                } else {
                    row.add("");
                }
            }

            rows.add(row);
        }
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
        List<GridRange> fullRange = Collections.singletonList(new GridRange()
                .setSheetId(0)
                .setStartRowIndex(0)
                .setStartColumnIndex(0)
        );
        List<GridRange> xHeaderRange = Collections.singletonList(new GridRange()
                .setSheetId(0)
                .setStartRowIndex(0)
                .setEndRowIndex(1)
                .setStartColumnIndex(1)
                .setEndColumnIndex(outputCharacters.size())
        );
        List<GridRange> yHeaderRange = Collections.singletonList(new GridRange()
                .setSheetId(0)
                .setStartRowIndex(1)
                .setEndRowIndex(inputCharacters.size() + 1)
                .setStartColumnIndex(0)
                .setEndColumnIndex(1)
        );

        List<Request> requests = Arrays.asList(
                new Request().setUpdateDimensionProperties(
                        new UpdateDimensionPropertiesRequest()
                                .setRange(
                                        new DimensionRange()
                                                .setSheetId(0)
                                                .setDimension("COLUMNS")
                                                .setStartIndex(0)
                                )
                                .setProperties(new DimensionProperties().setPixelSize(30))
                                .setFields("pixelSize")
                ),
                new Request().setUpdateDimensionProperties(
                        new UpdateDimensionPropertiesRequest()
                                .setRange(
                                        new DimensionRange()
                                                .setSheetId(0)
                                                .setDimension("ROWS")
                                                .setStartIndex(0)
                                )
                                .setProperties(new DimensionProperties().setPixelSize(30))
                                .setFields("pixelSize")
                ),
                new Request().setAddConditionalFormatRule(
                        new AddConditionalFormatRuleRequest()
                                .setRule(new ConditionalFormatRule()
                                        .setRanges(xHeaderRange)
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
                                        .setRanges(yHeaderRange)
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
        List<ValueRange> data = new ArrayList<>();
        data.add(new ValueRange().setRange("A1").setValues(rows));

        BatchUpdateValuesRequest batchUpdateValuesRequest = new BatchUpdateValuesRequest()
                .setValueInputOption("RAW")
                .setData(data);
        BatchUpdateValuesResponse batchUpdateValuesResponse = sheets.spreadsheets().values().batchUpdate(spreadsheetFile.getId(), batchUpdateValuesRequest).execute();
    }
}

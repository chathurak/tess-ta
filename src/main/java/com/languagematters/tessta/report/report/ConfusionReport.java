package com.languagematters.tessta.report.report;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;
import com.languagematters.tessta.report.model.ConfusionMap;

import java.io.IOException;
import java.util.*;

public class ConfusionReport {

    private ArrayList<String> characters;
    private ArrayList<String> subCharacters;

    private List<List<Object>> rows;

    public ConfusionReport(final ConfusionMap confusionMap, final HashSet<String> exBlock) {
        rows = new ArrayList<>();

        LinkedHashSet<String> exBlockLinked = new LinkedHashSet<>(exBlock);

        // Get characters and subCharacters
        characters = new ArrayList<>();
        subCharacters = new ArrayList<>();
        HashMap<String, Boolean> subCharacterAdded = new HashMap<>();
        for (String character : exBlockLinked) {
            if (confusionMap.containsOuterKey(character)) {
                characters.add(character);
                for (String subCharacter : exBlockLinked) {
                    if (confusionMap.containsInnerKey(character, subCharacter) && !subCharacterAdded.getOrDefault(character, Boolean.FALSE)) {
                        subCharacters.add(subCharacter);
                        subCharacterAdded.put(subCharacter, Boolean.TRUE);
                    }
                }
            }
        }

        // Create heading
        List<Object> heading = new ArrayList<>();
        heading.add(" ");
        heading.addAll(subCharacters);
        rows.add(heading);

        for (String character : characters) {
            List<Object> row = new ArrayList<>();
            row.add(character);

            for (String subCharacter : subCharacters) {
                if (confusionMap.containsInnerKey(character, subCharacter)) {
                    row.add(confusionMap.getCountValue(character, subCharacter));
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
                .setEndColumnIndex(subCharacters.size())
        );
        List<GridRange> yHeaderRange = Collections.singletonList(new GridRange()
                .setSheetId(0)
                .setStartRowIndex(1)
                .setEndRowIndex(characters.size() + 1)
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
                )
//                new Request().setAddConditionalFormatRule(
//                        new AddConditionalFormatRuleRequest()
//                                .setRule(new ConditionalFormatRule()
//                                        .setRanges(xHeaderRange)
//                                        .setBooleanRule(new BooleanRule()
//                                                .setCondition(new BooleanCondition()
//                                                        .setType("CUSTOM_FORMULA")
//                                                        .setValues(Collections.singletonList(
//                                                                new ConditionValue().setUserEnteredValue("=TRUE")
//                                                        ))
//                                                )
//                                                .setFormat(new CellFormat().setBackgroundColor(
//                                                        new Color()
//                                                                .setRed(0.937f)
//                                                                .setGreen(0.603f)
//                                                                .setBlue(0.603f)
//                                                ))
//                                        )
//                                )
//                                .setIndex(0)
//                ),
//                new Request().setAddConditionalFormatRule(
//                        new AddConditionalFormatRuleRequest()
//                                .setRule(new ConditionalFormatRule()
//                                        .setRanges(yHeaderRange)
//                                        .setBooleanRule(new BooleanRule()
//                                                .setCondition(new BooleanCondition()
//                                                        .setType("CUSTOM_FORMULA")
//                                                        .setValues(Collections.singletonList(
//                                                                new ConditionValue().setUserEnteredValue("=TRUE")
//                                                        ))
//                                                )
//                                                .setFormat(new CellFormat().setBackgroundColor(
//                                                        new Color()
//                                                                .setRed(0.647f)
//                                                                .setGreen(0.839f)
//                                                                .setBlue(0.654f)
//                                                ))
//                                        )
//                                )
//                                .setIndex(0)
//                )
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

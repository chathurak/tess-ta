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
import com.languagematters.tessta.report.model.CustomDiff;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DiffReport {

    private final List<List<Object>> rows;

    public DiffReport(@NotNull List<CustomDiff> deltas) {
        rows = new ArrayList<>();
        rows.add(Arrays.asList("Input text", "OCR output", "Type"));

        int i = 1;
        for (CustomDiff d : deltas) {
            List<Object> row = new ArrayList<>();

            String modifiedText = d.getText()
                    .replace("\n", "<n>")
                    .replace("\t", "<t>")
                    .replace("\r", "<r>")
                    .replace("\b", "<b>")
                    .replace(" ", "<s>");

            switch (d.getOperation()) {
                case EQUAL: {
                    row.add(modifiedText);
                    row.add(modifiedText);
                    row.add(d.getDescription());
                    break;
                }
                case INSERT: {
                    row.add(modifiedText);
                    row.add("");
                    row.add(d.getDescription());
                    break;
                }
                case DELETE: {
                    row.add("");
                    row.add(modifiedText);
                    row.add(d.getDescription());
                    break;
                }
                default:
                    break;
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
                .setEndColumnIndex(3)
        );

        List<Request> requests = Arrays.asList(
                new Request().setUpdateDimensionProperties(
                        new UpdateDimensionPropertiesRequest()
                                .setRange(
                                        new DimensionRange()
                                                .setSheetId(0)
                                                .setDimension("COLUMNS")
                                                .setStartIndex(0)
                                                .setEndIndex(2)
                                )
                                .setProperties(new DimensionProperties().setPixelSize(500))
                                .setFields("pixelSize")
                ),
                new Request().setUpdateDimensionProperties(
                        new UpdateDimensionPropertiesRequest()
                                .setRange(
                                        new DimensionRange()
                                                .setSheetId(0)
                                                .setDimension("COLUMNS")
                                                .setStartIndex(2)
                                                .setEndIndex(3)
                                )
                                .setProperties(new DimensionProperties().setPixelSize(100))
                                .setFields("pixelSize")
                ),
                new Request().setAddConditionalFormatRule(
                        new AddConditionalFormatRuleRequest()
                                .setRule(new ConditionalFormatRule()
                                        .setRanges(fullRange)
                                        .setBooleanRule(new BooleanRule()
                                                .setCondition(new BooleanCondition()
                                                        .setType("CUSTOM_FORMULA")
                                                        .setValues(Collections.singletonList(
                                                                new ConditionValue().setUserEnteredValue("=IFERROR(IF(SEARCH(\"Equal\",$C1,1)>0,\"TRUE\"),\"FALSE\")")
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
                ),
                new Request().setAddConditionalFormatRule(
                        new AddConditionalFormatRuleRequest()
                                .setRule(new ConditionalFormatRule()
                                        .setRanges(fullRange)
                                        .setBooleanRule(new BooleanRule()
                                                .setCondition(new BooleanCondition()
                                                        .setType("CUSTOM_FORMULA")
                                                        .setValues(Collections.singletonList(
                                                                new ConditionValue().setUserEnteredValue("=IFERROR(IF(SEARCH(\"Insert\",$C1,1)>0,\"TRUE\"),\"FALSE\")")
                                                        ))
                                                )
                                                .setFormat(new CellFormat().setBackgroundColor(
                                                        new Color()
                                                                .setRed(0.160f)
                                                                .setGreen(0.713f)
                                                                .setBlue(0.964f)
                                                ))
                                        )
                                )
                                .setIndex(0)
                ),
                new Request().setAddConditionalFormatRule(
                        new AddConditionalFormatRuleRequest()
                                .setRule(new ConditionalFormatRule()
                                        .setRanges(fullRange)
                                        .setBooleanRule(new BooleanRule()
                                                .setCondition(new BooleanCondition()
                                                        .setType("CUSTOM_FORMULA")
                                                        .setValues(Collections.singletonList(
                                                                new ConditionValue().setUserEnteredValue("=IFERROR(IF(SEARCH(\"Delete\",$C1,1)>0,\"TRUE\"),\"FALSE\")")
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

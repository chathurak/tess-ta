package com.languagematters.tessta.report.model;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;
import com.languagematters.tessta.report.service.DiffServices;
import com.languagematters.tessta.report.service.DriveServices;
import com.languagematters.tessta.report.service.SheetsServices;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DiffReport {

    private static Sheets sheetsService;
    private static Drive driveService;

    List<DiffServices.CustomDiff> deltas;

    List<List<Object>> rows;

    public DiffReport(@NotNull List<DiffServices.CustomDiff> deltas) throws IOException, GeneralSecurityException {
        sheetsService = SheetsServices.getSheetsService();
        driveService = DriveServices.getDriveService();

        this.deltas = deltas;

        rows = new ArrayList<>();
        rows.add(Arrays.asList("Input text", "OCR output", "Type"));

        int i = 1;
        for (DiffServices.CustomDiff d : deltas) {
            List<Object> row = new ArrayList<>();

            String modifiedText = d.text
                    .replace("\n", "<n>")
                    .replace("\t", "<t>")
                    .replace("\r", "<r>")
                    .replace("\b", "<b>")
                    .replace(" ", "<s>");

            switch (d.googleDiffOperation) {
                case EQUAL: {
                    row.add(modifiedText);
                    row.add(modifiedText);
                    row.add(d.description);
                    break;
                }
                case INSERT: {
                    row.add(modifiedText);
                    row.add("");
                    row.add(d.description);
                    break;
                }
                case DELETE: {
                    row.add("");
                    row.add(modifiedText);
                    row.add(d.description);
                    break;
                }
            }

            rows.add(row);
        }
    }

    public void writeReport(String parentDirId, String outputFileName) throws IOException {
        // Create spreadsheet
        File spreadsheetMetadata = new File();
        spreadsheetMetadata.setName(outputFileName);
        spreadsheetMetadata.setMimeType("application/vnd.google-apps.spreadsheet");
        spreadsheetMetadata.setParents(Collections.singletonList(parentDirId));
        File spreadsheetFile = driveService.files()
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
        BatchUpdateSpreadsheetResponse batchUpdateSpreadsheetResponse = sheetsService.spreadsheets()
                .batchUpdate(spreadsheetFile.getId(), batchUpdateSpreadsheetRequest)
                .execute();

        // Update values
        List<ValueRange> data = new ArrayList<ValueRange>();
        data.add(new ValueRange().setRange("A1").setValues(rows));

        BatchUpdateValuesRequest batchUpdateValuesRequest = new BatchUpdateValuesRequest()
                .setValueInputOption("RAW")
                .setData(data);
        BatchUpdateValuesResponse batchUpdateValuesResponse = sheetsService.spreadsheets().values().batchUpdate(spreadsheetFile.getId(), batchUpdateValuesRequest).execute();
    }
}

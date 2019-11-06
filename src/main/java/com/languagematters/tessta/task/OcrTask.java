package com.languagematters.tessta.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.sheets.v4.Sheets;
import com.languagematters.tessta.admin.service.GoogleAPIServices;
import com.languagematters.tessta.ocr.service.ImageServices;
import com.languagematters.tessta.ocr.service.OcrServices;
import com.languagematters.tessta.report.model.ConfusionMap;
import com.languagematters.tessta.report.model.DiffList;
import com.languagematters.tessta.report.report.ConfusionReport;
import com.languagematters.tessta.report.report.ConfusionSummaryReport;
import com.languagematters.tessta.report.report.DiffReport;
import com.languagematters.tessta.report.service.ConfusionMapServices;
import com.languagematters.tessta.report.service.DiffServices;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

@Component
@Scope("prototype")
@RequiredArgsConstructor
public class OcrTask {

    private final Connection connection;

    private final ImageServices imageServices;
    private final OcrServices ocrServices;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${app.tempstore}")
    private String tempStorePath;

    @Setter
    private int documentId;
    @Setter
    private String taskId;
    @Setter
    private String username;
    @Setter
    private String accessToken;
    @Setter
    private String originalFileName;

    public void process() {
        try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery("select * from exblock")){
            Drive drive = GoogleAPIServices.getDriveInstance(accessToken);
            Sheets sheets = GoogleAPIServices.getSheetsInstance(accessToken);

            // Create output gdrive directory
            System.out.printf("%s : Create output gdrive directory%n", taskId);
            com.google.api.services.drive.model.File parentDirMetadata = new com.google.api.services.drive.model.File();
            parentDirMetadata.setName(taskId);
            parentDirMetadata.setMimeType("application/vnd.google-apps.folder");
            com.google.api.services.drive.model.File parentDir = drive.files()
                    .create(parentDirMetadata)
                    .setFields("id")
                    .execute();

            File taskDir = new File(String.format("%s/%s/%s/%s", tempStorePath, username, documentId, taskId));
            File originalFile = new File(String.format("%s/%s/%s/%s", tempStorePath, username, documentId, originalFileName));
            String extension = FilenameUtils.getExtension(originalFile.getName()).toLowerCase();

            // Create task directory
            System.out.printf("%s : Create task directory%n", taskId);
            Files.createDirectories(taskDir.toPath());
            System.out.println(taskDir.toPath());

            // Text to image
            System.out.printf("%s : Text to image%n", taskId);
            imageServices.text2Image(getExecutor(), originalFile.getAbsolutePath(), taskDir.getAbsolutePath() + "/out");

            // OCR
            if(extension.equals("txt")){
                System.out.printf("%s : OCR%n", taskId);
                ocrServices.ocr(getExecutor(), taskDir.getAbsolutePath() + "/out.tif", taskDir.getAbsolutePath() + "/output");
            }

            if(extension.equals("txt")){
                // Comparison
                System.out.printf("%s : Comparison%n", taskId);
                DiffList diffList = DiffServices.getDefaultDiff(originalFile.getAbsolutePath(), taskDir.getAbsolutePath() + "/output.txt");
                objectMapper.writeValue(new File(String.format("%s/diff_list.json", taskDir.getAbsolutePath())), diffList);

                // Confusion Matrix
                System.out.printf("%s : Confusion Matrix%n", taskId);
                ConfusionMap confusionMap = ConfusionMapServices.getConfusionMap(diffList);
                objectMapper.writeValue(new File(String.format("%s/confusion_map.json", taskDir.getAbsolutePath())), confusionMap);

                // DiffReport
                System.out.printf("%s : Generate DiffReport%n", taskId);
                DiffReport diffReport = new DiffReport(diffList.getCustomDiffs());
                System.out.printf("%s : Write DiffReport%n", taskId);
                diffReport.writeReport(drive, sheets, parentDir.getId(), "diff");

                HashSet<String> exBlock = new HashSet<>();
                while (rs.next()) {
                    exBlock.add(rs.getString("character"));
                }

                // ConfusionReport
                System.out.printf("%s : Generate ConfusionReport%n", taskId);
                ConfusionReport confusionReport = new ConfusionReport(confusionMap, exBlock);
                System.out.printf("%s : Write ConfusionReport%n", taskId);
                confusionReport.writeReport(drive, sheets, parentDir.getId(), "confusion");

                // ConfusionSummaryReport
                System.out.printf("%s : Generate ConfusionSummaryReport%n", taskId);
                ConfusionSummaryReport confusionSummaryReport = new ConfusionSummaryReport(confusionMap, exBlock);
                System.out.printf("%s : Write ConfusionSummaryReport%n", taskId);
                confusionSummaryReport.writeReport(drive, sheets, parentDir.getId(), "confusion_summary");
            }

            // Log
            // TODO : Generate Json and save as ./log.json

            // Upload file list
            System.out.printf("%s : Upload files%n", taskId);
            ArrayList<Upload> uploadFileList = new ArrayList<>();

            if(extension.equals("txt")){
                uploadFileList.add(new Upload(originalFileName, "text/plain", originalFile.getAbsolutePath()));
                uploadFileList.add(new Upload("out.box", "application/octet-stream", taskDir.getAbsolutePath() + "/out.box"));
                uploadFileList.add(new Upload("out.tif", "image/tiff", taskDir.getAbsolutePath() + "/out.tif"));
            }else if(extension.equals("jpeg")){
                uploadFileList.add(new Upload(originalFileName, "image/jpeg", originalFile.getAbsolutePath()));
            }
            uploadFileList.add(new Upload("output.txt", "text/plain", taskDir.getAbsolutePath() + "/output.txt"));

            // TODO : Enable after generating log.json
            // uploadFileList.add(new Upload("log.json", "application/json", taskDir.getAbsolutePath() + "/log.json"));

            // Upload files
            for (Upload upload : uploadFileList) {
                System.out.printf("%s : Uploading %s%n", taskId, upload.getName());
                com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
                fileMetadata.setName(upload.getName());
                fileMetadata.setParents(Collections.singletonList(parentDir.getId()));
                File filePath = new File(upload.getPath());
                FileContent mediaContent = new FileContent(upload.getContent(), filePath);
                try {
                    drive.files().create(fileMetadata, mediaContent)
                            .setFields("id")
                            .execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.out.printf("%s : Process completed%n", taskId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Executor getExecutor() throws IOException {
        Executor executor = new DefaultExecutor();
        executor.setExitValue(0);

        PipedOutputStream outputStream = new PipedOutputStream();
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
        executor.setStreamHandler(streamHandler);

        DataInputStream dataInputStream = new DataInputStream(new PipedInputStream(outputStream));
        PipeParser pipeParser = new PipeParser(dataInputStream);
        pipeParser.start();

        return executor;
    }

    @Data
    private class Upload {

        @NonNull
        private String name;
        @NonNull
        private String content;
        @NonNull
        private String path;

    }

    @RequiredArgsConstructor
    private class PipeParser extends Thread {

        @NonNull
        private InputStream inputStream;

        @Override
        public void run() {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

                String line = null;

                while ((line = br.readLine()) != null) {
                    if (line.equalsIgnoreCase("quit")) {
                        break;
                    }
                    System.out.printf("Exec output : %s\n", line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
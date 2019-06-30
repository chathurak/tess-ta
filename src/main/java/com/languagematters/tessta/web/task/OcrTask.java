package com.languagematters.tessta.web.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.sheets.v4.Sheets;
import com.languagematters.tessta.ocr.service.ImageServices;
import com.languagematters.tessta.ocr.service.OcrServices;
import com.languagematters.tessta.report.model.ConfusionMap;
import com.languagematters.tessta.report.model.DiffList;
import com.languagematters.tessta.report.model.report.ConfusionReport;
import com.languagematters.tessta.report.model.report.ConfusionSummaryReport;
import com.languagematters.tessta.report.model.report.DiffReport;
import com.languagematters.tessta.report.service.ConfusionMapServices;
import com.languagematters.tessta.report.service.DiffServices;
import com.languagematters.tessta.report.service.GoogleAPIServices;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.PumpStreamHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;

@Component
@Scope("prototype")
public class OcrTask {

    private final ImageServices imageServices;
    private final OcrServices ocrServices;

    private final ObjectMapper objectMapper;

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

    @Autowired
    public OcrTask(final ImageServices imageServices, final OcrServices ocrServices) {
        this.imageServices = imageServices;
        this.ocrServices = ocrServices;

        this.objectMapper = new ObjectMapper();
    }

    public void process() {
        try {
//            Drive drive = GoogleAPIServices.getDriveInstance(accessToken);
//            Sheets sheets = GoogleAPIServices.getSheetsInstance(accessToken);

            // Create output gdrive directory
//            com.google.api.services.drive.model.File parentDirMetadata = new com.google.api.services.drive.model.File();
//            parentDirMetadata.setName(taskId);
//            parentDirMetadata.setMimeType("application/vnd.google-apps.folder");
//            com.google.api.services.drive.model.File parentDir = drive.files()
//                    .create(parentDirMetadata)
//                    .setFields("id")
//                    .execute();

            File taskDir = new File(String.format("%s/%s/%s/%s", tempStorePath, username, documentId, taskId));
            File originalFile = new File(String.format("%s/%s/%s/%s", tempStorePath, username, documentId, originalFileName));

            // Create task directory
            System.out.println("Create task directory");
            Files.createDirectories(taskDir.toPath());
            System.out.println(taskDir.toPath());

            // Text to image
            System.out.println("Text to image");
            imageServices.text2ImageDocker(getExecutor(), originalFile.getAbsolutePath(), taskDir.getAbsolutePath() + "/out");

            // OCR
            System.out.println("OCR");
            ocrServices.ocrDocker(getExecutor(), taskDir.getAbsolutePath() + "/out.tif", taskDir.getAbsolutePath() + "/output");

            // Comparison
            System.out.println("Comparison");
            DiffList diffList = DiffServices.getDefaultDiff(originalFile.getAbsolutePath(), taskDir.getAbsolutePath() + "/output.txt");
            objectMapper.writeValue(new File(String.format("%s/diff_list.json", taskDir.getAbsolutePath())), diffList);

            // Confusion Matrix
            System.out.println("Confusion Matrix");
            ConfusionMap confusionMap = ConfusionMapServices.getConfusionMap(diffList);
            objectMapper.writeValue(new File(String.format("%s/confusion_map.json", taskDir.getAbsolutePath())), confusionMap);

            // Save reports
            System.out.println("Save reports");
//            new DiffReport(diffList.getCustomDiffs()).writeReport(drive, sheets, parentDir.getId(), "diff");
//            new ConfusionReport(confusionMap).writeReport(drive, sheets, parentDir.getId(), "confusion");
//            new ConfusionSummaryReport(confusionMap).writeReport(drive, sheets, parentDir.getId(), "confusion_summary");

            // Log
            // TODO : Generate Json and save as ./log.json

            // Upload files
            System.out.println("Upload files");
            ArrayList<Upload> uploadFileList = new ArrayList<>();
            uploadFileList.add(new Upload(originalFileName, "text/plain", originalFile.getAbsolutePath()));
            uploadFileList.add(new Upload("out.box", "application/octet-stream", taskDir.getAbsolutePath() + "/out.box"));
            uploadFileList.add(new Upload("out.tif", "image/tiff", taskDir.getAbsolutePath() + "/out.tif"));
            uploadFileList.add(new Upload("output.txt", "text/plain", taskDir.getAbsolutePath() + "/output.txt"));
//            uploadFileList.add(new Upload("log.json", "application/json", taskDir.getAbsolutePath() + "/log.json"));

//            for (Upload upload : uploadFileList) {
//                com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
//                fileMetadata.setName(upload.getName());
//                fileMetadata.setParents(Collections.singletonList(parentDir.getId()));
//                File filePath = new File(upload.getPath());
//                FileContent mediaContent = new FileContent(upload.getContent(), filePath);
//                try {
//                    drive.files().create(fileMetadata, mediaContent)
//                            .setFields("id")
//                            .execute();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }

            System.out.printf("Process completed : %s\n", taskId);
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
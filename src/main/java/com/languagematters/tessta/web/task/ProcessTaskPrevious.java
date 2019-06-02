package com.languagematters.tessta.web.task;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.languagematters.tessta.ocr.service.ImageServices;
import com.languagematters.tessta.ocr.service.OcrServices;
import com.languagematters.tessta.report.model.ConfusionMap;
import com.languagematters.tessta.report.model.CustomDiff;
import com.languagematters.tessta.report.model.DiffList;
import com.languagematters.tessta.report.model.report.ConfusionReport;
import com.languagematters.tessta.report.model.report.ConfusionSummaryReport;
import com.languagematters.tessta.report.model.report.DiffReport;
import com.languagematters.tessta.report.service.ConfusionMapServices;
import com.languagematters.tessta.report.service.DiffServices;
import com.languagematters.tessta.report.service.GoogleAPIServices;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO : Make this class injected and not manually initiated
//@Component
//@Scope("prototype")
public class ProcessTaskPrevious implements Runnable {

    @Autowired
    private ImageServices imageServices;
    @Autowired
    private OcrServices ocrServices;

    @Value("${app.tempstore}")
    private String tempStorePath;

    // TODO : Autowire these
    private static Drive drive;
    private static com.google.api.services.drive.model.File tessRoot;

    @Setter
    private String pid;
    @Setter
    private String username;
    @Setter
    private String originalFileName;

    public ProcessTaskPrevious() {
        try {
            drive = GoogleAPIServices.getDriveInstance();
            tessRoot = GoogleAPIServices.getRoot();
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            // Create output directory
            com.google.api.services.drive.model.File parentDirMetadata = new com.google.api.services.drive.model.File();
            parentDirMetadata.setName(pid);
            parentDirMetadata.setMimeType("application/vnd.google-apps.folder");
            parentDirMetadata.setParents(Collections.singletonList(tessRoot.getId()));
            com.google.api.services.drive.model.File parentDir = drive.files()
                    .create(parentDirMetadata)
                    .setFields("id")
                    .execute();

            // Get fine extension
            String fileExtension = FilenameUtils.getExtension(tempStorePath + "/" + originalFileName);

            // Temp
            File tempDir = new File(String.format("%s/%s/%s", tempStorePath, username, pid));
            File tempFile = new File(String.format("%s/%s/%s/%s", tempStorePath, username, pid, originalFileName));

            // Text to image
            imageServices.text2Image(getExecutor(), tempFile.getAbsolutePath(), tempDir.getAbsolutePath() + "/out");

            // OCR
            ocrServices.ocr(getExecutor(), tempDir.getAbsolutePath() + "/out.tif", tempDir.getAbsolutePath() + "/output");

            // Comparison
            DiffList diffList = DiffServices.getDefaultDiff(tempFile.getAbsolutePath(), tempDir.getAbsolutePath() + "/output.txt");
            new DiffReport(diffList.getCustomDiffs()).writeReport(parentDir.getId(), "diff");

            // Confusion Matrix
            ConfusionMap confusionMap = ConfusionMapServices.getConfusionMap(diffList);
            new ConfusionReport(confusionMap).writeReport(parentDir.getId(), "confusion");
            new ConfusionSummaryReport(confusionMap).writeReport(parentDir.getId(), "confusion_summary");

            // Log
            // TODO : Generate Json and save as ./log.json

            // Upload files
            ArrayList<Upload> uploadFileList = new ArrayList<>();
            uploadFileList.add(new Upload("input.txt", "text/plain", tempFile.getAbsolutePath()));
            uploadFileList.add(new Upload("out.box", "application/octet-stream", tempDir.getAbsolutePath() + "/out.box"));
            uploadFileList.add(new Upload("out.tif", "image/tiff", tempDir.getAbsolutePath() + "/out.tif"));
            uploadFileList.add(new Upload("output.txt", "text/plain", tempDir.getAbsolutePath() + "/output.txt"));
            uploadFileList.add(new Upload("log.json", "application/json", tempDir.getAbsolutePath() + "/log.json"));

            for (Upload upload : uploadFileList) {
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

            System.out.println("Process completed : " + pid);
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

    private class PipeParser extends Thread {

        private InputStream inputStream;

        PipeParser(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        public void run() {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

                String line = null;

                while ((line = br.readLine()) != null) {
                    if (line.equalsIgnoreCase("quit")) {
                        break;
                    }
                    System.out.println("Exec output : " + line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
package com.languagematters.tessta.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.sheets.v4.Sheets;
import com.languagematters.tessta.config.AppProperties;
import com.languagematters.tessta.ocr.service.ImageServices;
import com.languagematters.tessta.ocr.service.OcrServices;
import com.languagematters.tessta.report.model.ConfusionMap;
import com.languagematters.tessta.report.model.DiffList;
import com.languagematters.tessta.report.service.DiffServices;
import com.languagematters.tessta.report.type.ConfusionReport;
import com.languagematters.tessta.report.type.ConfusionSummaryReport;
import com.languagematters.tessta.report.type.DiffReport;
import com.languagematters.tessta.service.GoogleAPIServices;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.lang.NonNull;
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
    private final AppProperties appProperties;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String T2I_OUTPUT_FILE_NAME = "Image_for_recog";
    private final String OCR_OUTPUT_FILE_NAME = "Tesseract_output";

    private int documentId;
    private String taskId;
    private String username;
    private String accessToken;
    private String originalFileName;

    public OcrTask(ImageServices imageServices, OcrServices ocrServices, AppProperties appProperties) {
        this.imageServices = imageServices;
        this.ocrServices = ocrServices;
        this.appProperties = appProperties;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public void process() {
        try {
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

            File taskDir = new File(String.format("%s/%s/%s/%s", appProperties.getStore().getTempstore(), username, documentId, taskId));
            File originalFile = new File(String.format("%s/%s/%s/%s", appProperties.getStore().getTempstore(), username, documentId, originalFileName));
            String extension = FilenameUtils.getExtension(originalFile.getName()).toLowerCase();

            // Create task directory
            System.out.printf("%s : Create task directory%n", taskId);
            Files.createDirectories(taskDir.toPath());
            System.out.println(taskDir.toPath());

            if (extension.equals("txt")) {
                // Text to image
                System.out.printf("%s : Text to image%n", taskId);
                imageServices.text2Image(getExecutor(), originalFile.getAbsolutePath(), String.format("%s/%s", taskDir.getAbsolutePath(), T2I_OUTPUT_FILE_NAME));
            } else if (extension.equals("tiff") || extension.equals("tif")) {
                File taskFile = new File(String.format("%s/%s.tif", taskDir.getAbsolutePath(), T2I_OUTPUT_FILE_NAME));
                FileUtils.copyFile(originalFile, taskFile);
            } else if (extension.equals("jpeg") || extension.equals("jpg")) {
                File taskFile = new File(String.format("%s/%s.tif", taskDir.getAbsolutePath(), T2I_OUTPUT_FILE_NAME));
                imageServices.jpeg2tiff(getExecutor(), originalFile.getAbsolutePath(), taskFile.getAbsolutePath());
            }

            // OCR
            System.out.printf("%s : OCR%n", taskId);
            ocrServices.ocr(getExecutor(), String.format("%s/%s.tif", taskDir.getAbsolutePath(), T2I_OUTPUT_FILE_NAME), String.format("%s/%s", taskDir.getAbsolutePath(), OCR_OUTPUT_FILE_NAME));

            if (extension.equals("txt")) {
                // Comparison
                System.out.printf("%s : Comparison%n", taskId);
                DiffList diffList = DiffServices.getDefaultDiff(originalFile.getAbsolutePath(), String.format("%s/%s.txt", taskDir.getAbsolutePath(), OCR_OUTPUT_FILE_NAME));
                objectMapper.writeValue(new File(String.format("%s/diff_list.json", taskDir.getAbsolutePath())), diffList);

                // Confusion Matrix
                System.out.printf("%s : Confusion Matrix%n", taskId);
                ConfusionMap confusionMap = new ConfusionMap(diffList);
                objectMapper.writeValue(new File(String.format("%s/confusion_map.json", taskDir.getAbsolutePath())), confusionMap);

                // DiffReport
                System.out.printf("%s : Generate DiffReport%n", taskId);
                DiffReport diffReport = new DiffReport(diffList.getCustomDiffs());
                System.out.printf("%s : Write DiffReport%n", taskId);
                diffReport.writeReport(drive, sheets, parentDir.getId(), "diff");

                // ConfusionReport
                System.out.printf("%s : Generate ConfusionReport%n", taskId);
                ConfusionReport confusionReport = new ConfusionReport(confusionMap);
                System.out.printf("%s : Write ConfusionReport%n", taskId);
                confusionReport.writeReport(drive, sheets, parentDir.getId(), "confusion");

                // ConfusionSummaryReport
                System.out.printf("%s : Generate ConfusionSummaryReport%n", taskId);
                ConfusionSummaryReport confusionSummaryReport = new ConfusionSummaryReport(confusionMap);
                System.out.printf("%s : Write ConfusionSummaryReport%n", taskId);
                confusionSummaryReport.writeReport(drive, sheets, parentDir.getId(), "confusion_summary");
            }

            // Log
            // TODO : Generate Json and save as ./log.json

            // Upload file list
            System.out.printf("%s : Upload files%n", taskId);
            ArrayList<Upload> uploadFileList = new ArrayList<>();

            if (extension.equals("txt")) {
                uploadFileList.add(new Upload(originalFileName, "text/plain", originalFile.getAbsolutePath()));
                uploadFileList.add(new Upload(String.format("%s.tif", T2I_OUTPUT_FILE_NAME), "image/tiff", String.format("%s/%s.tif", taskDir.getAbsolutePath(), T2I_OUTPUT_FILE_NAME)));
            } else if (extension.equals("jpeg") || extension.equals("jpg")) {
                uploadFileList.add(new Upload(originalFileName, "image/jpeg", originalFile.getAbsolutePath()));
            } else if (extension.equals("tiff")) {
                uploadFileList.add(new Upload(originalFileName, "image/tiff", originalFile.getAbsolutePath()));
            }
            uploadFileList.add(new Upload(String.format("%s.txt", OCR_OUTPUT_FILE_NAME), "text/plain", String.format("%s/%s.txt", taskDir.getAbsolutePath(), OCR_OUTPUT_FILE_NAME)));

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

    private class Upload {

        @NonNull
        private String name;
        @NonNull
        private String content;
        @NonNull
        private String path;

        public Upload(@NonNull String name, @NonNull String content, @NonNull String path) {
            this.name = name;
            this.content = content;
            this.path = path;
        }

        @NonNull
        public String getName() {
            return name;
        }

        public void setName(@NonNull String name) {
            this.name = name;
        }

        @NonNull
        public String getContent() {
            return content;
        }

        public void setContent(@NonNull String content) {
            this.content = content;
        }

        @NonNull
        public String getPath() {
            return path;
        }

        public void setPath(@NonNull String path) {
            this.path = path;
        }
    }

    private class PipeParser extends Thread {

        @NonNull
        private final InputStream inputStream;

        public PipeParser(@NonNull InputStream inputStream) {
            this.inputStream = inputStream;
        }

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
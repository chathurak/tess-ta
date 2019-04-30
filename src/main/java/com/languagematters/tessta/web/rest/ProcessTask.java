package com.languagematters.tessta.web.rest;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.languagematters.tessta.admin.type.EnvironmentVariable;
import com.languagematters.tessta.ocr.model.ConfusionMap;
import com.languagematters.tessta.ocr.model.OCRLog;
import com.languagematters.tessta.ocr.service.ConfusionMapService;
import com.languagematters.tessta.ocr.service.DiffService;
import com.languagematters.tessta.ocr.service.ImageService;
import com.languagematters.tessta.ocr.service.OCRService;
import com.languagematters.tessta.report.model.ConfusionReport;
import com.languagematters.tessta.report.model.ConfusionSummaryReport;
import com.languagematters.tessta.report.model.DiffReport;
import com.languagematters.tessta.report.service.DriveService;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Component
@Scope("prototype")
public class ProcessTask implements Runnable {

    private static Drive driveService;
    private static com.google.api.services.drive.model.File tessLibraryDir;
    private Jedis jedis = new Jedis("localhost");
    private String pid;
    private String originalFileName;
    // Executor
    private DefaultExecutor executor;
    private PipedOutputStream outputStream;
    private PumpStreamHandler streamHandler;
    private DataInputStream dataInputStream;
    private PipeParser pipeParser;

    public ProcessTask() {
        try {
            driveService = DriveService.getDriveService();
            tessLibraryDir = DriveService.getTessLibrary();
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    @Override
    public void run() {
        try {
            // Create output directory
            com.google.api.services.drive.model.File parentDirMetadata = new com.google.api.services.drive.model.File();
            parentDirMetadata.setName(pid);
            parentDirMetadata.setMimeType("application/vnd.google-apps.folder");
            parentDirMetadata.setParents(Collections.singletonList(tessLibraryDir.getId()));
            com.google.api.services.drive.model.File parentDir = driveService.files()
                    .create(parentDirMetadata)
                    .setFields("id")
                    .execute();

            // Get fine extension
            String fileExtension = FilenameUtils.getExtension(jedis.get(EnvironmentVariable.TESS_STORAGE_TEMP.toString()) + "/" + originalFileName);

            // Temp
            File tempDir = new File(jedis.get(EnvironmentVariable.TESS_STORAGE_TEMP.toString()) + "/" + pid);
            File tempFile = new File(jedis.get(EnvironmentVariable.TESS_STORAGE_TEMP.toString()) + "/" + pid + "/" + originalFileName);

            // Text to image
            resetExecutor();
            ImageService.text2Image(executor, tempFile.getAbsolutePath(), tempDir.getAbsolutePath() + "/out");

            // OCR
            resetExecutor();
            OCRService.ocr(executor, tempDir.getAbsolutePath() + "/out.tif", tempDir.getAbsolutePath() + "/output");

            // Comparison
            List<DiffService.CustomDiff> deltas = DiffService.getDefaultDiff(tempFile.getAbsolutePath(), tempDir.getAbsolutePath() + "/output.txt");
            new DiffReport(deltas).writeReport(parentDir.getId(), "diff");

            // Confusion Matrix
            ConfusionMap confusionMap = ConfusionMapService.getConfusionMap(deltas);
            new ConfusionReport(confusionMap).writeReport(parentDir.getId(), "confusion");
            new ConfusionSummaryReport(confusionMap).writeReport(parentDir.getId(), "confusion_summary");

            // Log
            OCRLog ocrLog = new OCRLog();
            ocrLog.setOriginalFileName(originalFileName);
            try (Writer writer = new FileWriter(tempDir.getAbsolutePath() + "/log.json")) {
                Gson gson = new GsonBuilder().create();
                gson.toJson(ocrLog, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }

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
                    driveService.files().create(fileMetadata, mediaContent)
                            .setFields("id")
                            .execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Delete temp dir
            FileUtils.deleteQuietly(tempDir);

            System.out.println("Process completed : " + pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetExecutor() throws IOException {
        executor = new DefaultExecutor();
        executor.setExitValue(0);
        outputStream = new PipedOutputStream();
        streamHandler = new PumpStreamHandler(outputStream);
        executor.setStreamHandler(streamHandler);
        dataInputStream = new DataInputStream(new PipedInputStream(outputStream));
        pipeParser = new PipeParser(dataInputStream);
        pipeParser.start();
    }

    private class Upload {
        private String name;
        private String content;
        private String path;

        public Upload(String name, String content, String path) {
            this.name = name;
            this.content = content;
            this.path = path;
        }

        public String getName() {
            return name;
        }

        public String getContent() {
            return content;
        }

        public String getPath() {
            return path;
        }

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
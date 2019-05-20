package com.languagematters.tessta.web.task;

import com.languagematters.tessta.ocr.service.ImageServices;
import com.languagematters.tessta.ocr.service.OcrServices;
import com.languagematters.tessta.report.model.ConfusionMap;
import com.languagematters.tessta.report.service.ConfusionMapServices;
import com.languagematters.tessta.report.service.DiffServices;
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
import java.util.List;

@Component
@Scope("prototype")
public class OcrTask {

    private final ImageServices imageServices;
    private final OcrServices ocrServices;

    @Value("${app.tempstore}")
    private String tempStorePath;

    @Setter
    private String fileId;
    @Setter
    private String taskId;
    @Setter
    private String username;
    @Setter
    private String originalFileName;

    @Autowired
    public OcrTask(final ImageServices imageServices, final OcrServices ocrServices) {
        this.imageServices = imageServices;
        this.ocrServices = ocrServices;
    }

    public void process() {
        try {
            // Temp
            File taskDir = new File(String.format("%s/%s/%s/%s", tempStorePath, username, fileId, taskId));
            File taskFile = new File(String.format("%s/%s/%s/%s/%s", tempStorePath, username, fileId, taskId, originalFileName));

            // Text to image
            imageServices.text2Image(getExecutor(), taskFile.getAbsolutePath(), taskDir.getAbsolutePath() + "/out");

            // OCR
            ocrServices.ocr(getExecutor(), taskDir.getAbsolutePath() + "/out.tif", taskDir.getAbsolutePath() + "/output");

            // Comparison
            List<DiffServices.CustomDiff> deltas = DiffServices.getDefaultDiff(taskFile.getAbsolutePath(), taskDir.getAbsolutePath() + "/output.txt");
            // TODO : Save diff report

            // Confusion Matrix
            ConfusionMap confusionMap = ConfusionMapServices.getConfusionMap(deltas);
            // TODO : Save confusion report
            // TODO : Save confusion summay report

            // Log
            // TODO : Generate Json and save as ./log.json

            System.out.println("Process completed : " + taskId);
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
                    System.out.println("Exec output : " + line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
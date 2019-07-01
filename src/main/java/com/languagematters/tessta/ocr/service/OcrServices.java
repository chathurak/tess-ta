package com.languagematters.tessta.ocr.service;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.environment.EnvironmentUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OcrServices {

    @Value("${app.tessdata}")
    private String tessdataPath;

    public void ocr(Executor executor, String inputPath, String outputPath) throws Exception {
        CommandLine cmdLine = new CommandLine("tesseract");
        cmdLine.addArgument("--tessdata-dir");
        cmdLine.addArgument(tessdataPath);
        cmdLine.addArgument(inputPath);
        cmdLine.addArgument(outputPath);
        cmdLine.addArgument("-l");
        cmdLine.addArgument("sin");
        cmdLine.addArgument("segdemo");
        cmdLine.addArgument("inter");

        // Custom environment
        Map<String, String> customEnvironment = EnvironmentUtils.getProcEnvironment();
        customEnvironment.put("PANGOCAIRO_BACKEND", "fc");

        // Execute
        executor.execute(cmdLine, customEnvironment);
    }

    public void ocrDocker(Executor executor, String inputPath, String outputPath) throws Exception {
        CommandLine cmdLine = new CommandLine("docker");
        cmdLine.addArgument("exec");
        cmdLine.addArgument("tesseract-daemon");
        cmdLine.addArgument("tesseract");
        cmdLine.addArgument("--tessdata-dir");
        cmdLine.addArgument(tessdataPath);
        cmdLine.addArgument(inputPath);
        cmdLine.addArgument(outputPath);
        cmdLine.addArgument("-l");
        cmdLine.addArgument("sin");
        cmdLine.addArgument("segdemo");
        cmdLine.addArgument("inter");

        // Custom environment
        Map<String, String> customEnvironment = EnvironmentUtils.getProcEnvironment();
        customEnvironment.put("PANGOCAIRO_BACKEND", "fc");

        // Execute
        executor.execute(cmdLine, customEnvironment);
    }

}
package com.languagematters.tessta.ocr.service;

import com.languagematters.tessta.admin.service.OsServices;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.environment.EnvironmentUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ImageServices {

    @Value("${app.tessdata}")
    private String tessdataPath;

    public void text2Image(Executor executor, String inputPath, String outputPath) throws Exception {
        CommandLine cmdLine = null;
        cmdLine = new CommandLine("text2image");
        cmdLine.addArgument("--text");
        cmdLine.addArgument(inputPath);
        cmdLine.addArgument("--outputbase");
        cmdLine.addArgument(outputPath);
        cmdLine.addArgument("--fonts_dir");
        cmdLine.addArgument(tessdataPath);
        cmdLine.addArgument("--font");
        cmdLine.addArgument("Iskoola Pota", false);

        Map<String, String> customEnvironment = null;
        customEnvironment = EnvironmentUtils.getProcEnvironment();
        customEnvironment.put("PANGOCAIRO_BACKEND", "fc");

        executor.execute(cmdLine, customEnvironment);
    }

    public void text2ImageDocker(Executor executor, String inputPath, String outputPath) throws Exception {
        CommandLine cmdLine = new CommandLine("docker");
        cmdLine.addArgument("exec");
        cmdLine.addArgument("tesseract-daemon");
        cmdLine.addArgument("text2image");
        cmdLine.addArgument("--text");
        cmdLine.addArgument(inputPath);
        cmdLine.addArgument("--outputbase");
        cmdLine.addArgument(outputPath);
        cmdLine.addArgument("--fonts_dir");
        cmdLine.addArgument(tessdataPath);
        cmdLine.addArgument("--font");
        cmdLine.addArgument("Iskoola Pota", false);

        Map<String, String> customEnvironment = null;
        customEnvironment = EnvironmentUtils.getProcEnvironment();
        customEnvironment.put("PANGOCAIRO_BACKEND", "fc");

        executor.execute(cmdLine, customEnvironment);
    }

}

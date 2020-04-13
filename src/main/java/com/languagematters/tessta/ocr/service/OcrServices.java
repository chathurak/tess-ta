package com.languagematters.tessta.ocr.service;

import com.languagematters.tessta.config.AppProperties;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.environment.EnvironmentUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OcrServices {

    private final AppProperties appProperties;

    public OcrServices(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public void ocr(Executor executor, String inputPath, String outputPath) throws Exception {
        CommandLine cmdLine = null;
        cmdLine = new CommandLine("tesseract");
        cmdLine.addArgument("--tessdata-dir");
        cmdLine.addArgument(appProperties.getStore().getTessdata());
        cmdLine.addArgument(inputPath);
        cmdLine.addArgument(outputPath);
        cmdLine.addArgument("-l");
        cmdLine.addArgument("sin+eng+tam");
        cmdLine.addArgument("segdemo");
        cmdLine.addArgument("inter");

        // Custom environment
        Map<String, String> customEnvironment = EnvironmentUtils.getProcEnvironment();
        customEnvironment.put("PANGOCAIRO_BACKEND", "fc");

        // Execute
        executor.execute(cmdLine, customEnvironment);
    }

}
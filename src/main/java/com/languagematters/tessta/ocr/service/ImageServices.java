package com.languagematters.tessta.ocr.service;

import com.languagematters.tessta.config.AppProperties;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.environment.EnvironmentUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ImageServices {

    private final AppProperties appProperties;

    public ImageServices(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public void text2Image(Executor executor, String inputPath, String outputPath) throws Exception {
        CommandLine cmdLine = null;
        cmdLine = new CommandLine("text2image");
        cmdLine.addArgument("--text");
        cmdLine.addArgument(inputPath);
        cmdLine.addArgument("--outputbase");
        cmdLine.addArgument(outputPath);
        cmdLine.addArgument("--fonts_dir");
        cmdLine.addArgument(appProperties.getStore().getTessdata());
        cmdLine.addArgument("--font");
        cmdLine.addArgument("Iskoola Pota", false);

        Map<String, String> customEnvironment = null;
        customEnvironment = EnvironmentUtils.getProcEnvironment();
        customEnvironment.put("PANGOCAIRO_BACKEND", "fc");

        executor.execute(cmdLine, customEnvironment);
    }

    public void jpeg2tiff(Executor executor, String inputPath, String outputPath) throws Exception {
        CommandLine cmdLine = null;
        cmdLine = new CommandLine("convert");
        cmdLine.addArgument(inputPath);
        cmdLine.addArgument(outputPath);

        Map<String, String> customEnvironment = null;
        customEnvironment = EnvironmentUtils.getProcEnvironment();
        customEnvironment.put("PANGOCAIRO_BACKEND", "fc");

        executor.execute(cmdLine, customEnvironment);
    }

}

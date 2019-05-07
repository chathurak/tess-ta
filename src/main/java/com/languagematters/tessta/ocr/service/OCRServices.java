package com.languagematters.tessta.ocr.service;

import com.languagematters.tessta.EnvironmentVariable;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.environment.EnvironmentUtils;
import redis.clients.jedis.Jedis;

import java.util.Map;

public class OCRServices {

    public static void ocr(Executor executor, String inputPath, String outputPath) throws Exception {
        Jedis jedis = new Jedis("localhost");

        CommandLine cmdLine = new CommandLine("tesseract");
        cmdLine.addArgument("--tessdata-dir");
        cmdLine.addArgument(jedis.get(EnvironmentVariable.TESS_TESSDATA.toString()));
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
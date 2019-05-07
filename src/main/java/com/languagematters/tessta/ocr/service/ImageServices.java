package com.languagematters.tessta.ocr.service;

import com.languagematters.tessta.EnvironmentVariable;
import com.languagematters.tessta.admin.service.OsServices;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.environment.EnvironmentUtils;
import redis.clients.jedis.Jedis;

import java.util.Map;

public class ImageServices {

    public static void text2Image(Executor executor, String inputPath, String outputPath) throws Exception {
        Jedis jedis = new Jedis("localhost");

        CommandLine cmdLine = null;

        if (OsServices.isMac()) {
            cmdLine = new CommandLine("docker");
            cmdLine.addArgument("exec");
            cmdLine.addArgument("t4cmp");
            cmdLine.addArgument("text2image");
        } else {
            cmdLine = new CommandLine("text2image");
        }

        cmdLine.addArgument("--text");
        cmdLine.addArgument(inputPath);
        cmdLine.addArgument("--outputbase");
        cmdLine.addArgument(outputPath);
        cmdLine.addArgument("--fonts_dir");
        cmdLine.addArgument(jedis.get(EnvironmentVariable.TESS_TESSDATA.toString()));
        cmdLine.addArgument("--font");
        cmdLine.addArgument("Iskoola Pota", false);

        Map<String, String> customEnvironment = null;
        customEnvironment = EnvironmentUtils.getProcEnvironment();
        customEnvironment.put("PANGOCAIRO_BACKEND", "fc");

        executor.execute(cmdLine, customEnvironment);
    }

}

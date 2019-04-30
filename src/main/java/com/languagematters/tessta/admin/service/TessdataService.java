package com.languagematters.tessta.admin.service;

import com.google.gson.Gson;
import com.languagematters.tessta.admin.model.TrainedDataProperties;
import com.languagematters.tessta.admin.type.EnvironmentVariable;
import com.languagematters.tessta.admin.type.TessdataParam;
import org.apache.commons.io.FileUtils;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.util.ArrayList;

public class TessdataService {

    private static Jedis jedis = new Jedis("localhost");

    public static ArrayList<TrainedDataProperties> getTrainedData() {
        ArrayList<TrainedDataProperties> result = new ArrayList<>();

        File file = new File(jedis.get(EnvironmentVariable.TESS_STORAGE.toString()) + "/trained_data");
        File[] files = file.listFiles(f -> {
            if (f.isDirectory()) {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(f.getAbsolutePath() + "/properties.json"));
                    TrainedDataProperties trainedDataProperties = new Gson().fromJson(bufferedReader, TrainedDataProperties.class);
                    result.add(trainedDataProperties);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            return f.isDirectory();
        });

        return result;
    }

    public static void setDefaultTrainedData(String id) throws IOException {
        jedis.set(TessdataParam.CURRENT_TRAINED_DATA_ID.toString(), id);

        File from = new File(jedis.get(EnvironmentVariable.TESS_STORAGE_TRAINED_DATA.toString()) + "/" + id + "/sin.traineddata");
        File to = new File(jedis.get(EnvironmentVariable.TESS_TESSDATA.toString()) + "/sin.traineddata");
        FileUtils.copyFile(from, to);
    }

    public static void setTrainedDataName(String id, String newName) {
        // TODO : Save new name
    }

    public static void setTrainedDataDescription(String id, String newDescription) {
        // TODO : Save new description
    }

    public static void deleteTrainedData(String id) throws IOException {
        File dirToDelete = FileUtils.getFile(jedis.get(EnvironmentVariable.TESS_STORAGE_TRAINED_DATA.toString()) + "/" + id);
        FileUtils.deleteDirectory(dirToDelete);
    }
}

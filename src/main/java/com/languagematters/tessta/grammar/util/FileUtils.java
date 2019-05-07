package com.languagematters.tessta.grammar.util;

import java.io.*;
import java.util.HashMap;

public class FileUtils {
    // Get Test Directory List
    public static HashMap<String, Object> getTestDirList(String libraryPath) {
        HashMap<String, Object> map = new HashMap<>();

        // Open test directories in '/library'
        File dir = new File(libraryPath);
        String[] libraryDirList = dir.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });

        // For each in dir list
        if (libraryDirList.length == 0) {
            System.out.println("The directory is empty");
        } else {
            for (String libraryDir : libraryDirList) {
                // Open test inputs in library dir
                File dir2 = new File(libraryPath + "/" + libraryDir);
                String[] testDirList = dir2.list();
                // For each in dir list
                for (String testDir : testDirList) {

                    // Add new item
                    String name = libraryDir + " " + testDir;
                    String path = String.format("%s/%s/%s", libraryPath, libraryDir, testDir);
                    map.put(name, path);
                }
            }
        }

        return map;
    }

    // Load text file
    public static String loadTextFile(String path) {
        String text = "";
        BufferedReader br = null;

        try {
            String line;
            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                text += line + '\n';
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return text;
    }

    // Save text file
    public static void saveTextFile(String text, String path) {
        FileWriter out = null;

        try {
            out = new FileWriter(path);
            out.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}

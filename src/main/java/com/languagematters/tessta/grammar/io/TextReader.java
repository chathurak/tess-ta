package com.languagematters.tessta.grammar.io;



import com.languagematters.tessta.grammar.utils.Convert;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextReader {
    public TextReader() {

    }

    // Read document as a String
    public static String readAsString(String fileName) {
        return Convert.toString(readLines(fileName));
    }

    // Read document as a list of lines
    public static List<String> readLines(String fileName) {
        String line = null;
        List<String> lines = new ArrayList<>();

        try {
            // Open and readAsString file.
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }

            // Close files.
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }

        return lines;
    }
}

package com.languagematters.tessta.grammar.io;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class TextWriter {
    // Write text to a file
    public static void write(String fileName, String text) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(fileName, "UTF-8");
            writer.println(text);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    // Append text to a file
    public static void append(String fileName, String text) {
        write(fileName, TextReader.readAsString(fileName) + '\n' + text);
    }
}

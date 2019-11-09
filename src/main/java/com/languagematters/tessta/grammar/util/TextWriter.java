package com.languagematters.tessta.grammar.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class TextWriter {
    // Write text to a file
    public static void write(String fileName, String text) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(fileName, StandardCharsets.UTF_8);
            writer.println(text);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Append text to a file
    public static void append(String fileName, String text) {
        write(fileName, TextReader.readAsString(fileName) + '\n' + text);
    }
}

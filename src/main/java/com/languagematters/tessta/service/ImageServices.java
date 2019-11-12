package com.languagematters.tessta.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ImageServices {

    @Value("${app.tessdata}")
    private String tessdataPath;

    public void text2Image(String inputPath, String outputDirPath, String outputFileName) throws Exception {

    }

}
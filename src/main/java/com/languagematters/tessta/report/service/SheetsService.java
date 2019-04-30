package com.languagematters.tessta.report.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.languagematters.tessta.report.util.GoogleAuthorizeUtil;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class SheetsService {
    private static final String APPLICATION_NAME = "TesseractTA";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public static Sheets getSheetsService() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, GoogleAuthorizeUtil.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}

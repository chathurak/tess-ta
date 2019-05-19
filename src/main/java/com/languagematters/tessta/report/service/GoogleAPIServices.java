package com.languagematters.tessta.report.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.sheets.v4.Sheets;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static com.languagematters.tessta.report.util.GoogleAuthorizeUtil.getCredentials;

public class GoogleAPIServices {
    private static final String APPLICATION_NAME = "TesseractTA";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public static Drive getDriveInstance() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static Sheets getSheetsInstance() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static File getRoot() throws IOException, GeneralSecurityException {
        return GoogleAPIServices.getDriveInstance().files().list()
                .setQ("name='TessTA'")
                .setSpaces("drive")
                .setFields("nextPageToken, files(id, name)")
                .execute().getFiles().get(0);
    }

}

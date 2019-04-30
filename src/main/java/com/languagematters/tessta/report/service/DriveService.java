package com.languagematters.tessta.report.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static com.languagematters.tessta.report.util.GoogleAuthorizeUtil.getCredentials;

public class DriveService {
    private static final String APPLICATION_NAME = "TesseractTA";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public static Drive getDriveService() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static File getRoot() throws IOException, GeneralSecurityException {
        return DriveService.getDriveService().files().list()
                .setQ("name='TessTA'")
                .setSpaces("drive")
                .setFields("nextPageToken, files(id, name)")
                .execute().getFiles().get(0);

    }

    public static File getTessLibrary() throws IOException, GeneralSecurityException {
        return DriveService.getDriveService().files().list()
                .setQ("name='tess_library'")
                .setSpaces("drive")
                .setFields("nextPageToken, files(id, name)")
                .execute().getFiles().get(0);

    }
}

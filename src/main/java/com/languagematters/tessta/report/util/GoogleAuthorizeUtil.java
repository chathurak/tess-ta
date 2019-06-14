package com.languagematters.tessta.report.util;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;

import java.io.IOException;

public class GoogleAuthorizeUtil {

    public static Credential getCredentials(String accessToken) throws IOException {
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod());
        credential.setAccessToken(accessToken);
        return credential;
    }

}
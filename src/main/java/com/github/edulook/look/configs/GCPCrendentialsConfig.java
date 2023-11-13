package com.github.edulook.look.configs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.store.FileDataStoreFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.classroom.Classroom;
import com.google.api.services.classroom.ClassroomScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;

import static com.google.api.client.googleapis.auth.oauth2.GoogleOAuthConstants.AUTHORIZATION_SERVER_URL;


@Configuration
public class GCPCrendentialsConfig {

    private static final List<String> SCOPES = List.of(
        ClassroomScopes.CLASSROOM_COURSES_READONLY,
        ClassroomScopes.CLASSROOM_COURSES,
        ClassroomScopes.CLASSROOM_ROSTERS,
        ClassroomScopes.CLASSROOM_ANNOUNCEMENTS,
        ClassroomScopes.CLASSROOM_PROFILE_EMAILS
    );


    @Bean
    public Classroom classroomClientConfig() throws GeneralSecurityException, IOException {
        var httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        var jsonFactory = GsonFactory.getDefaultInstance();
        var DATA_STORE_DIR = new File(System.getProperty("user.home"), ".store/look");
        var DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        
        // name: look-api-integration - type: Desktop -  client id: 279941193466-64ja...
        var CLIENT_ID = "279941193466-64ja6mia1h00es4c0v4pk8mse8qhpb36.apps.googleusercontent.com";
        var CLIENT_SECRET = "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";

        AuthorizationCodeFlow flow =
            new AuthorizationCodeFlow.Builder(
                BearerToken.authorizationHeaderAccessMethod(),
                httpTransport,
                jsonFactory,
                new GenericUrl("https://oauth2.googleapis.com/token"),
                new ClientParametersAuthentication(CLIENT_ID, CLIENT_SECRET),
                CLIENT_ID,
                "https://accounts.google.com/o/oauth2/auth"
            )
            .setScopes(SCOPES)
            .setDataStoreFactory(DATA_STORE_FACTORY)
            .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder()
            .setHost("localhost")
            .setPort(8085)
            .build();
        
        var credentials = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

       return new Classroom
            .Builder(httpTransport, jsonFactory, credentials)
            .setApplicationName(APPLICATION_NAME)
            .build();
    }
}
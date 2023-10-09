package com.github.edulook.look.configs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

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

// https://github.com/gcbrandao/gcp-auth/blob/master/src/main/java/com/gcbrandao/gcp/auth/gcpauth/GCPAccessToken.java
// https://developers.google.com/api-client-library/java/google-api-java-client/oauth2?hl=pt-br
// https://stackoverflow.com/questions/57972607/what-is-the-alternative-to-the-deprecated-googlecredential

@Configuration
public class GCPCrendentialsConfig {


    private static final List<String> SCOPES = List.of(
        ClassroomScopes.CLASSROOM_COURSES_READONLY,
        ClassroomScopes.CLASSROOM_COURSES,
        ClassroomScopes.CLASSROOM_ROSTERS,
        ClassroomScopes.CLASSROOM_ANNOUNCEMENTS,
        ClassroomScopes.CLASSROOM_PROFILE_EMAILS
    );

    private static final String APPLICATION_NAME = "look-service-account";
    private static final String USER_EMAIL = "look-service-account@look-project-400817.iam.gserviceaccount.com";

    @Bean
    public Classroom classroomClientConfig() throws GeneralSecurityException, IOException {
        var httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        var jsonFactory = GsonFactory.getDefaultInstance();

        File credentialsFile = ResourceUtils.getFile("classpath:credentials.json");

        var delegatedCredentials = ServiceAccountCredentials
            .fromStream(new FileInputStream(credentialsFile))
            .createDelegated(USER_EMAIL)
            .createScoped(SCOPES);

        var credentials = new HttpCredentialsAdapter(delegatedCredentials);
           
        
        Classroom service = new Classroom.Builder(httpTransport, jsonFactory, credentials)
            .setApplicationName(APPLICATION_NAME)
            .build();
        
        return service;
    }

    @Bean
    public GoogleCredentials googleCredentialsDI() throws IOException, GeneralSecurityException {
        return ServiceAccountCredentials.getApplicationDefault();
    }
}
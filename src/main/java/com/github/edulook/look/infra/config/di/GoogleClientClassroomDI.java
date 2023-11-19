package com.github.edulook.look.infra.config.di;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.classroom.Classroom;
import com.google.api.services.classroom.ClassroomScopes;


@Configuration
public class GoogleClientClassroomDI {

    @Value("${look.application.name}")
    private String applicationName;

    @Value("${look.cloud.gcp.server.client.id}")
    private String clientId;

    @Value("${look.cloud.gcp.server.client.secret}")
    private String clientSecret;

    @Value("${look.cloud.gcp.server.token-url}")
    private String encodedUrl;
    
    @Value("${look.cloud.gcp.server.authorization-encoded-url}")
    private String authorizationServerEncodedUrl;

    @Value("${look.cloud.gcp.server.client.storage}")
    private String storageFolder;

    @Value("${server.port:8085}")
    private int serverPort;

    @Value("${server.address:localhost}")
    private String address;

    @Bean
    public Classroom classroomClientConfig() throws GeneralSecurityException, IOException {
        var httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        var jsonFactory = GsonFactory.getDefaultInstance();
        var dataStoraFactory = new FileDataStoreFactory(new File(storageFolder));

        var flow = new AuthorizationCodeFlow.Builder(
                BearerToken.authorizationHeaderAccessMethod(),
                httpTransport,
                jsonFactory,
                new GenericUrl(encodedUrl),
                new ClientParametersAuthentication(clientId, clientSecret),
                clientId,
                authorizationServerEncodedUrl
            )
            .setScopes(ClassroomScopes.all())
            .setDataStoreFactory(dataStoraFactory)
            .build();


        var receiver = new LocalServerReceiver.Builder()
            .setHost(address)
            .setPort(serverPort)
            .build();
        
        var credentials = new AuthorizationCodeInstalledApp(flow, receiver)
            .authorize("user");

       return new Classroom
            .Builder(httpTransport, jsonFactory, credentials)
            .setApplicationName(applicationName)
            .build();
    }
}
package com.github.edulook.look.infra.config.di;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.appengine.datastore.AppEngineDataStoreFactory;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.classroom.ClassroomScopes;
import com.google.api.services.drive.DriveScopes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

@Configuration
public class AuthorizationFlowDI {
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
    public Credential credentialConfig() throws GeneralSecurityException, IOException {
        var httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        var jsonFactory = GsonFactory.getDefaultInstance();

        // https://developers.google.com/api-client-library/java/google-api-java-client/oauth2?hl=pt-br#data_store
        var dataStorageFactory = new FileDataStoreFactory(new File(storageFolder));

        var scopes = new ArrayList<String>() {{
            addAll(DriveScopes.all());
            addAll(ClassroomScopes.all());
        }};

        var flow = new AuthorizationCodeFlow.Builder(
                BearerToken.authorizationHeaderAccessMethod(),
                httpTransport,
                jsonFactory,
                new GenericUrl(encodedUrl),
                new ClientParametersAuthentication(clientId, clientSecret),
                clientId,
                authorizationServerEncodedUrl
            )
            .setScopes(scopes)
            .setDataStoreFactory(dataStorageFactory)
            .build();

        var receiver = new LocalServerReceiver.Builder()
            .setHost(address)
            .setPort(serverPort)
            .build();

        return new AuthorizationCodeInstalledApp(flow, receiver)
            .authorize("user");
    }
}

package com.github.edulook.look.infra.config.database;

import com.google.auth.oauth2.GoogleCredentials;
// import com.google.firebase.FirebaseApp;
// import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

// @Configuration
public class FireBaseConfig {

    // @Value("${firebase.serviceAccountKeyPath}")
    // private String serviceAccountKeyPath;

    // @Value("${firebase.databaseUrl}")
    // private String databaseUrl;

    // @Bean
    // public FirebaseApp firebaseApp() throws IOException {
    //     FileInputStream serviceAccount =
    //             new FileInputStream(serviceAccountKeyPath);

    //     FirebaseOptions options = new FirebaseOptions.Builder()
    //             .setCredentials(GoogleCredentials.fromStream(serviceAccount))
    //             .setDatabaseUrl(databaseUrl)
    //             .build();

    //     return FirebaseApp.initializeApp(options);
    // }
}

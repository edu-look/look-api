package com.github.edulook.look.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.util.Collections;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.edulook.look.LookApplication;
import com.github.edulook.look.clients.GoogleClassroomClient;
import com.github.edulook.look.responses.CourseResponse;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.gax.core.CredentialsProvider;
import com.google.api.services.classroom.Classroom;
import com.google.api.services.classroom.ClassroomScopes;
import com.google.api.services.classroom.model.Course;
import com.google.api.services.classroom.model.ListCoursesResponse;
import com.google.auth.oauth2.GoogleCredentials;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("api/classroom")
public class GoogleClassroomController {

    private final GoogleClassroomClient googleClassroomClient;

    private final Classroom client;
    private final GoogleCredentials credential;

    @GetMapping("/v1/courses/{id}")
    public Mono<CourseResponse> getCourseById(@PathVariable String id) {
        log.debug("Curso {} ", id);
        return googleClassroomClient.findCourseInfoById(id);
    }

    @GetMapping("/v1/courses")
    public ResponseEntity<?> getAllCourses() throws Exception {
    
        var response = client.courses().list()
            .setPageSize(5)
            .execute();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/v1/token")
    public String token() throws IOException, GeneralSecurityException {
       return String.valueOf(credential);
    }
}

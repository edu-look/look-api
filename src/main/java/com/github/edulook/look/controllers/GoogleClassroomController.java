package com.github.edulook.look.controllers;


import com.github.edulook.look.clients.GoogleClassroomClient;
import com.github.edulook.look.responses.CourseResponse;
import com.github.edulook.look.responses.ListOfCourses;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.google.api.services.classroom.ClassroomScopes;
import com.google.api.services.classroom.model.*;
import com.google.api.services.classroom.Classroom;

import java.util.List;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("api/classroom")
public class GoogleClassroomController {

    GoogleClassroomClient googleClassroomClient;
    private final Classroom service;

    @GetMapping("/v1/courses/{id}")
    public Mono<CourseResponse> getCourseById (@PathVariable String id){
        log.debug("Curso {} ", id);
        return googleClassroomClient.findCourseInfoById(id);
    }

    @GetMapping("/v1/courses")
    public Flux<ListOfCourses> getAllCourses(){
        return googleClassroomClient.getAllCourses();
    }

    @GetMapping("courses")
    public List<Course> getAllCoursesRaw() {
        try {
            var response = service.courses().list()
                .setPageSize(10)
                .execute();
            
            return response.getCourses();
        }
        catch(Exception e) {
            log.error("{}", e);
        }

        return List.of();
    }
}

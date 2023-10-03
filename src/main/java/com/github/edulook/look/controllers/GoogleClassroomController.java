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

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("api/classroom")
public class GoogleClassroomController {

    GoogleClassroomClient googleClassroomClient;

    @GetMapping("/v1/courses/{id}")
    public Mono<CourseResponse> getCourseById (@PathVariable String id){
        log.debug("Curso {} ", id);
        return googleClassroomClient.findCourseInfoById(id);
    }

    @GetMapping("/v1/courses")
    public Flux<ListOfCourses> getAllCourses(){
        return googleClassroomClient.getAllCourses();
    }

}

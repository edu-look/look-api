package com.github.edulook.look.controllers;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.github.edulook.look.clients.GoogleClassroomClient;
import com.github.edulook.look.dtos.CourseDto;
import com.github.edulook.look.responses.CourseResponse;
import com.github.edulook.look.services.CourseService;
import com.google.api.services.classroom.Classroom;
import com.google.api.services.classroom.model.Course;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
@AllArgsConstructor
public class GoogleClassroomController {

    private final GoogleClassroomClient googleClassroomClient;
    private final Classroom client;
    private final CourseService courseService;


    @GetMapping("/v1/courses/{id}")
    public Mono<CourseResponse> getCourseById(@PathVariable String id) {
        log.debug("Curso {} ", id);
        return googleClassroomClient.findCourseInfoById(id);
    }


    public ResponseEntity<List<CourseDto>> listCourses() {
        try {
            List<Course> courses = courseService.listCourses();

            List<CourseDto> courseDTOs = courses.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(courseDTOs);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private CourseDto convertToDTO(Course course) {
        CourseDto courseDTO = new CourseDto();
        courseDTO.setId(course.getId());
        courseDTO.setName(course.getName());
        courseDTO.setDescription(course.getDescription());
        return courseDTO;
    }

    @GetMapping("/v1/courses")
    public ResponseEntity<?> getAllCourses() throws Exception {

        var response = client.courses().list()
            .setPageSize(5)
            .execute();

        return ResponseEntity.ok().body(response);
    }
}

package com.github.edulook.look.endpoint;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.edulook.look.endpoint.internal.mapper.course.CourseAndDTOMapper;
import com.github.edulook.look.endpoint.io.course.CourseDTO;
import com.github.edulook.look.service.course.CourseService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("v1/courses")
public class CourseEndpoint {

    private final CourseService courseService;
    private final CourseAndDTOMapper courseAndDTOMapper;


    @GetMapping
    public List<CourseDTO> getCourses(@RequestParam(name = "id") String userLogged) throws IOException {
        log.info("Student {} ", userLogged);
        return courseAndDTOMapper
            .toDtoList(courseService.listCourses(userLogged));
    }
}

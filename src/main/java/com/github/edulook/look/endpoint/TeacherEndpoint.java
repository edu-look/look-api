package com.github.edulook.look.endpoint;

import com.github.edulook.look.core.exceptions.ResourceNotFoundException;
import com.github.edulook.look.endpoint.internal.mapper.course.CourseAndDTOMapper;
import com.github.edulook.look.endpoint.io.course.CourseDTO;
import com.github.edulook.look.endpoint.io.shared.UserAuthDTO;
import com.github.edulook.look.service.CourseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("v1/courses")
public class TeacherEndpoint {
    private final CourseService courseService;
    private final CourseAndDTOMapper courseAndDTOMapper;

    public TeacherEndpoint(CourseService courseService, CourseAndDTOMapper courseAndDTOMapper) {
        this.courseService = courseService;
        this.courseAndDTOMapper = courseAndDTOMapper;
    }

    @ResponseBody
    @GetMapping(value = "class", produces = "application/json")
    public List<CourseDTO> getCoursesTeacher(@RequestAttribute("user") UserAuthDTO user) throws IOException {
        log.info("user logged: {}", user.id());

        var courses =  courseAndDTOMapper.toDTOList(courseService.listCoursesTeacher(user.id()));

        if(courses.isEmpty()) {
            throw new ResourceNotFoundException(String.format("courses not found to '%s' teacher id", user.id()));
        }

        return courses;
    }
}

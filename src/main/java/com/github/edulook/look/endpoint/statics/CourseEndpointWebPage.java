package com.github.edulook.look.endpoint.statics;

import com.github.edulook.look.core.exceptions.ResourceNotFoundException;
import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.endpoint.exceptions.data.ForbiddenMVCException;
import com.github.edulook.look.endpoint.internal.mapper.course.CourseAndDTOMapper;
import com.github.edulook.look.endpoint.io.course.CourseDTO;
import com.github.edulook.look.endpoint.io.shared.UserAuthDTO;
import com.github.edulook.look.service.CourseService;
import com.github.edulook.look.service.TeacherService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Controller
@RequestMapping("v1/courses")
@AllArgsConstructor
public class CourseEndpointWebPage {

    private final CourseService courseService;
    private final CourseAndDTOMapper courseAndDTOMapper;
    private final TeacherService teacherService;

    @GetMapping("{courseId}/materials/{materialId}/edit")
    public String editMaterial(@PathVariable String courseId,
                               @PathVariable String materialId,
                               @RequestAttribute("user") UserAuthDTO user,
                               Model model) {
        if (!teacherService.isTeacherCourseOwner(courseId, user)) {
            throw new ForbiddenMVCException("access denied");
        }

        log.info("user {}", user.id());

        return "edit.html";
    }

    @GetMapping(value = "class", produces = "text/html")
    public String getCoursesTeacherHtml(@RequestAttribute("user") UserAuthDTO user, Model model) throws IOException {
        log.info("user {}", user.id());

        var courses =  courseAndDTOMapper
                .toDTOList(courseService.listCoursesTeacher(user.id()));

        if(courses.isEmpty())
            throw new ForbiddenMVCException("access denied");

        model.addAttribute("courses", courses);

        return "courseList.html";
    }

    @GetMapping(value = "class", produces = "application/json")
    @ResponseBody
    public List<CourseDTO> getCoursesTeacherJson(@RequestAttribute("user") UserAuthDTO user) throws IOException {
        log.info("user logged: {}", user.id());

        var courses =  courseAndDTOMapper
                .toDTOList(courseService.listCoursesTeacher(user.id()));

        if(courses.isEmpty())
            throw new ResourceNotFoundException(String.format("courses not found to '%s' teacher id", user.id()));

        return courses;
    }
}



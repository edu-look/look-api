package com.github.edulook.look.endpoint.statics;

import com.github.edulook.look.core.exceptions.ResourceNotFoundException;
import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.endpoint.exceptions.data.ForbiddenMVCException;
import com.github.edulook.look.endpoint.io.shared.UserAuthDTO;
import com.github.edulook.look.service.CourseService;
import com.github.edulook.look.service.TeacherService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Controller
@RequestMapping("v1/courses")
@AllArgsConstructor
public class CourseEndpointWebPage {

    private final CourseService courseService;
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

}

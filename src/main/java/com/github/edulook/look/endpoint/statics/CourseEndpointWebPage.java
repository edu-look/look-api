package com.github.edulook.look.endpoint.statics;

import com.github.edulook.look.core.exceptions.ResourceNotFoundException;
import com.github.edulook.look.endpoint.exceptions.data.ForbiddenMVCException;
import com.github.edulook.look.endpoint.internal.mapper.course.CourseAndDTOMapper;
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
    public String getCoursesTeacher(@RequestAttribute("user") UserAuthDTO user, Model model) {
        log.info("user {}", user.id());

        var courses =  courseAndDTOMapper.toDTOList(courseService.listCoursesTeacher(user.id()));

        if(courses.isEmpty())
            throw new ForbiddenMVCException("access denied");

        model.addAttribute("courses", courses);

        return "courseList.html";
    }

    @GetMapping("class/{courseId}/materials")
    public String listAllWorkMaterials(@PathVariable String courseId,
                                       @RequestAttribute("user") UserAuthDTO user,
                                       Model model) {
        log.info("user logged: {}", user.id());
        log.info("materials to course: {}", courseId);

        return "accessibilityClass.html";
    }

}



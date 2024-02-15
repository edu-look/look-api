package com.github.edulook.look.endpoint.statics;

import com.github.edulook.look.endpoint.exceptions.data.ForbiddenMVCException;
import com.github.edulook.look.endpoint.internal.mapper.course.CourseAndDTOMapper;
import com.github.edulook.look.endpoint.io.shared.UserAuthDTO;
import com.github.edulook.look.service.CourseService;
import com.github.edulook.look.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("v1/courses")
public class CourseEndpointWebPage {
    private final CourseService courseService;
    private final CourseAndDTOMapper courseAndDTOMapper;
    private final TeacherService teacherService;

    public CourseEndpointWebPage(CourseService courseService, CourseAndDTOMapper courseAndDTOMapper, TeacherService teacherService) {
        this.courseService = courseService;
        this.courseAndDTOMapper = courseAndDTOMapper;
        this.teacherService = teacherService;
    }

    @GetMapping("{courseId}/materials/{materialId}/edit")
    public String editMaterial(@PathVariable String courseId,
                               @PathVariable String materialId,
                               @RequestAttribute("user") UserAuthDTO user) {
        log.info("user {} [course: {}, material: {}]", user.id(), courseId, materialId);
        if (!teacherService.isTeacherCourseOwner(courseId, user)) {
            throw new ForbiddenMVCException("access denied");
        }

        return View.EDIT_PAGE;
    }

    @GetMapping(value = "class", produces = "text/html")
    public String getCourses(@RequestAttribute("user") UserAuthDTO user) {
        log.info("user {}", user.id());
        var courses =  courseAndDTOMapper.toDTOList(courseService.listCoursesTeacher(user.id()));

        if(courses.isEmpty())
            throw new ForbiddenMVCException("access denied");

        return View.COURSES_PAGE;
    }

    @GetMapping("class/{courseId}/materials")
    public String getWorkMaterials(@PathVariable String courseId,
                                   @RequestAttribute("user") UserAuthDTO user) {
        log.info("user logged: {}", user.id());
        log.info("materials to course: {}", courseId);

        return View.COURSE_WORKSPACE_PAGE;
    }
}



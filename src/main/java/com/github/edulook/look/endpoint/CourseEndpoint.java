package com.github.edulook.look.endpoint;

import com.github.edulook.look.core.model.Course.Announcement;
import com.github.edulook.look.core.model.Course.WorkMaterial;
import com.github.edulook.look.endpoint.internal.mapper.course.CourseAndDTOMapper;
import com.github.edulook.look.endpoint.internal.mapper.shared.OAuth2AndUserAuthDTOMapper;
import com.github.edulook.look.endpoint.io.course.CourseDTO;
import com.github.edulook.look.endpoint.io.shared.UserAuthDTO;
import com.github.edulook.look.service.CourseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("v1/courses")
public class CourseEndpoint {

    private final CourseService courseService;
    private final CourseAndDTOMapper courseAndDTOMapper;

    public CourseEndpoint(CourseService courseService, CourseAndDTOMapper courseAndDTOMapper) {
        this.courseService = courseService;
        this.courseAndDTOMapper = courseAndDTOMapper;
    }

    @GetMapping
    public List<CourseDTO> getCourses(@RequestAttribute("user") UserAuthDTO user) throws IOException {
        log.info("user logged: {}", user.id());

        return courseAndDTOMapper
            .toDTOList(courseService.listCourses(user.id()));
    }

    @GetMapping("{courseId}/announcements")
    public List<Announcement> listAllAnnouncements(@PathVariable String courseId, @RequestAttribute("user") UserAuthDTO user) {
        log.info("user logged: {}", user.id());
        log.info("announcements to course: {}", courseId);

        return courseService
           .listAllAnnouncements(courseId, user.id());
    }

    @GetMapping("{courseId}/materials")
    public List<WorkMaterial> listAllWorkMaterials(@PathVariable String courseId, @RequestAttribute("user") UserAuthDTO user) {
        log.info("user logged: {}", user.id());
        log.info("materials to course: {}", courseId);

        return courseService.listAllWorkMaterials(courseId, user.jwt().token());
    }
}

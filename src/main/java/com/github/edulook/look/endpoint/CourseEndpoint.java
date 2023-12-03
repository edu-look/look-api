package com.github.edulook.look.endpoint;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.edulook.look.core.model.Course.Announcement;
import com.github.edulook.look.core.model.Course.WorkMaterial;
import com.github.edulook.look.endpoint.internal.mapper.course.CourseAndDTOMapper;
import com.github.edulook.look.endpoint.internal.mapper.shared.OAuth2AndUserAuthDTOMapper;
import com.github.edulook.look.endpoint.io.course.CourseDTO;
import com.github.edulook.look.service.CourseService;
import com.github.edulook.look.service.TeacherService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("v1/courses")
public class CourseEndpoint {

    private final CourseService courseService;
    
    private final CourseAndDTOMapper courseAndDTOMapper;
    private final OAuth2AndUserAuthDTOMapper oAuthMapper;

    @GetMapping
    public List<CourseDTO> getCourses(@AuthenticationPrincipal OAuth2User oAuth2User) throws IOException {
        var user = oAuthMapper.toDTO(oAuth2User);

        log.info("user logged: {}", user.id());

        return courseAndDTOMapper
            .toDTOList(courseService.listCourses(user.id()));
    }

    @GetMapping("{courseId}/announcements")
    public List<Announcement> listAllAnnouncements(@PathVariable String courseId, @AuthenticationPrincipal OAuth2User oAuth2User) {
        var user = oAuthMapper.toDTO(oAuth2User);

        log.info("user logged: {}", user.id());
        log.info("announcements to course: {}", courseId);

        return courseService
           .listAllAnnouncements(courseId, user.id());
    }

    @GetMapping("{courseId}/materials")
    public List<WorkMaterial> listAllWorkMaterials(@PathVariable String courseId, @AuthenticationPrincipal OAuth2User oAuth2User) {
        var user = oAuthMapper.toDTO(oAuth2User);

        log.info("user logged: {}", user.id());
        log.info("materials to course: {}", courseId);

        return courseService.listAllWorkMaterials(courseId, user.jwt().token());
    }
}

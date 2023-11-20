package com.github.edulook.look.endpoint;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.edulook.look.core.model.Course.Announcement;
import com.github.edulook.look.endpoint.internal.mapper.shared.OAuth2AndUserAuthDTOMapper;
import com.github.edulook.look.service.TeacherService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("v1/teachers")
public class TeacherEndpoint {
    private final TeacherService teacherService;
    private final OAuth2AndUserAuthDTOMapper oAuthMapper;

    @GetMapping("announcement/{courseId}")
    public List<Announcement> listAllAnnouncements(@PathVariable String courseId, @AuthenticationPrincipal OAuth2User oAuth2User) {
        var user = oAuthMapper.toDTO(oAuth2User);
        log.info("user logged: {}", user.id());
        log.info("announcements to course: {}", courseId);

        return teacherService
           .listAllAnnouncements(courseId, user.id());
    }
}

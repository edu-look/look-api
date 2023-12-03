package com.github.edulook.look.infra.repository.course;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.model.Course.Announcement;
import com.github.edulook.look.core.model.Teacher;
import com.github.edulook.look.core.repository.course.GetCourseAnnouncement;
import com.github.edulook.look.core.repository.teacher.GetTeacher;
import com.google.api.services.classroom.Classroom;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("GetTeacherAnnouncement::class")
public class GetCourseAnnouncementImpl implements GetCourseAnnouncement {
    
    @Autowired
    private Classroom classroom;

    @Autowired
    @Qualifier("GetTeacher::Class")
    private GetTeacher getTeacher;

    public List<Announcement> getAllAnnouncementByCourse(Course course) {
        try {
            var announcements = getAnnouncementsFromClassroom(course)
                .parallelStream()
                .filter(it -> selectCourseOwnerAnnouncement(it, course))
                .toList();
            
            if(announcements.isEmpty())
                return List.of();

            return toAnnouncementsCore(
                announcements,
                course,
                getTeacher(course, getTeacherId(announcements))
            );

        } catch (IOException e) {
            log.error("announcement error", e);
            return List.of();
        }        
    }

    private Boolean selectCourseOwnerAnnouncement(
        com.google.api.services.classroom.model.Announcement announcement, 
        Course course
    ) {
        return  announcement
            .getCreatorUserId()
            .equalsIgnoreCase(course.getOwnerId());
    }

    private List<Announcement> toAnnouncementsCore(
        List<com.google.api.services.classroom.model.Announcement> announcements,
        Course course,
        Teacher teacher
    ) {
        return announcements
            .stream()
            .map(it -> Announcement.builder()
                .id(it.getId())
                .courseId(course.getId())
                .owner(teacher.getName())
                .content(it.getText())
                .createdAt(it.getCreationTime())
                .build()
            )
            .toList();
    }

    private List<com.google.api.services.classroom.model.Announcement> getAnnouncementsFromClassroom(Course course) throws IOException {
        var request = Optional.ofNullable(classroom
            .courses()
            .announcements()
            .list(course.getId())
            .execute());
        
        if(request.isEmpty()) {
            return List.of();
        }

        var announcements = Optional
            .ofNullable(request.get().getAnnouncements());

        return announcements
            .orElse(List.of());
    }

    private String getTeacherId(List<com.google.api.services.classroom.model.Announcement> announcements) {
        return announcements
            .stream()
            .findFirst()
            .get()
            .getCreatorUserId();
    }

    private Teacher getTeacher(Course course, String teacherId) {
        return getTeacher
            .getTeacherFromCourseById(course.getId(), teacherId)
            .orElse(new Teacher());
    }
}
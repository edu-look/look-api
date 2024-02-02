package com.github.edulook.look.infra.repository.http.course;

import com.github.edulook.look.core.exceptions.ResourceNotFoundException;
import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.model.Announcement;
import com.github.edulook.look.core.model.Teacher;
import com.github.edulook.look.core.repository.course.GetCourseAnnouncement;
import com.github.edulook.look.core.repository.teacher.GetTeacher;
import com.google.api.services.classroom.Classroom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component("GetCourseAnnouncementHTTP::Class")
public class GetCourseAnnouncementHTTP implements GetCourseAnnouncement {
    private final Classroom classroom;
    private final GetTeacher getTeacher;

    public GetCourseAnnouncementHTTP(Classroom classroom,
                                     @Qualifier("GetTeacherHTTP::Class") GetTeacher getTeacher) {
        this.classroom = classroom;
        this.getTeacher = getTeacher;
    }

    public List<Announcement> getAllAnnouncementByCourse(Course course) {
        try {
            var announcements = getAnnouncementsFromClassroom(course)
                .parallelStream()
                .filter(it -> selectCourseOwnerAnnouncement(it, course))
                .toList();
            
            if(announcements.isEmpty())
                throw new ResourceNotFoundException("announcements not found");

            var teacherIdOptional = getTeacherId(announcements);
            if (teacherIdOptional.isEmpty())
                throw new ResourceNotFoundException("teacher id not found");

            return toAnnouncementsCore(
                announcements,
                course,
                getTeacher(course, teacherIdOptional.get())
            );

        } catch (IOException e) {
            log.error("error:: ", e);
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

    private Optional<String> getTeacherId(List<com.google.api.services.classroom.model.Announcement> announcements) {
        var announcementsOptional =  announcements
            .stream()
            .findFirst();

        return announcementsOptional
            .map(com.google.api.services.classroom.model.Announcement::getCreatorUserId);
    }

    private Teacher getTeacher(Course course, String teacherId) {
        return getTeacher
            .getTeacherFromCourseById(course.getId(), teacherId)
            .orElse(new Teacher());
    }
}

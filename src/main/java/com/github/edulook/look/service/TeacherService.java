package com.github.edulook.look.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.edulook.look.core.model.Course.Announcement;
import com.github.edulook.look.core.repository.CourseRepository;
import com.github.edulook.look.core.repository.TeacherRepository;

import lombok.AllArgsConstructor;

/**
 * Facade teacher service 
 */
@Service
@AllArgsConstructor
public class TeacherService {
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;

    public List<Announcement> listAllAnnouncements(String courseId, String studentId) {
        var course = courseRepository
            .findOneCourseByStudentId(courseId, studentId);

        if(course.isEmpty()) {
            return List.of();
        }

        return teacherRepository
            .getAllAnnouncementByCourse(course.get());
    }
}

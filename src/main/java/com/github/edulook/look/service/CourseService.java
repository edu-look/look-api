package com.github.edulook.look.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.model.Course.Announcement;
import com.github.edulook.look.core.model.Course.WorkMaterial;
import com.github.edulook.look.core.repository.CourseRepository;
import com.github.edulook.look.core.repository.TeacherRepository;

import lombok.AllArgsConstructor;

/**
 * Facade courses service 
 */
@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public List<Course> listCourses(String studentId) throws IOException {
       return courseRepository.findCoursesByStudentId(studentId);
    }

    public List<WorkMaterial> listAllWorkMaterials(String courseId, String access) {
        if(courseId == null) {
            return List.of();
        }

        var course = Course.builder()
            .id(courseId)
            .build();

        return courseRepository.listAllWorkMaterial(course, access);
    }


    public List<Announcement> listAllAnnouncements(String courseId, String studentId) {
        var course = courseRepository
            .findOneCourseByStudentId(courseId, studentId);

        if(course.isEmpty()) {
            return List.of();
        }

        return courseRepository
            .getAllAnnouncementByCourse(course.get());
    }
}

package com.github.edulook.look.service;

import com.github.edulook.look.core.repository.CourseRepository;
import com.github.edulook.look.core.repository.TeacherRepository;
import com.github.edulook.look.endpoint.io.shared.UserAuthDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;

    public TeacherService(CourseRepository courseRepository, TeacherRepository teacherRepository) {
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
    }

    @Cacheable("isTeacherCourseOwner")
    public Boolean isTeacherCourseOwner(String courseId, UserAuthDTO user) {
        return teacherRepository
            .getTeacherFromCourseById(courseId, user.id())
            .isPresent();
    }
}

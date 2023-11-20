package com.github.edulook.look.infra.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.repository.CourseRepository;
import com.github.edulook.look.core.repository.course.GetCourse;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CourseRepositoryImpl implements CourseRepository {
    
    @Autowired
    @Qualifier("GetCourse::Class")
    private GetCourse getCourse;

    @Override
    public List<Course> findCoursesByStudentId(String studentId) {
       return getCourse.findCoursesByStudentId(studentId);
    }

    @Override
    public Optional<Course> findOneCourseByStudentId(String courseId, String studentId) {
       return getCourse.findOneCourseByStudentId(courseId, studentId);
    }  
}

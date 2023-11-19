package com.github.edulook.look.service;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.usecases.course.GetCourse;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
/**
 * Facade courses service 
 */
public class CourseService {

    private final GetCourse getCourse;

    public List<Course> listCourses(String studentId) throws IOException {
       return getCourse.findCoursesByStudentId(studentId);
    }
}

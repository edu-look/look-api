package com.github.edulook.look.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.repository.CourseRepository;

import lombok.AllArgsConstructor;

/**
 * Facade courses service 
 */
@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepository repository;

    public List<Course> listCourses(String studentId) throws IOException {
       return repository.findCoursesByStudentId(studentId);
    }
}

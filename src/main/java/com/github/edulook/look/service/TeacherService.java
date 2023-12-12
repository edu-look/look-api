package com.github.edulook.look.service;

import com.github.edulook.look.core.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Facade teacher service 
 */
@Service(value="TeacherService::Class")
@AllArgsConstructor
public class TeacherService {
    private final CourseRepository courseRepository;
}

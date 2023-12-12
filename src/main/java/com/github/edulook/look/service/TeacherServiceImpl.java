package com.github.edulook.look.service;

import com.github.edulook.look.core.service.TeacherService;
import org.springframework.stereotype.Service;

import com.github.edulook.look.core.repository.CourseRepository;

import lombok.AllArgsConstructor;

/**
 * Facade teacher service 
 */
@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final CourseRepository courseRepository;
}

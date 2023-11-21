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
}

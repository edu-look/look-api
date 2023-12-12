package com.github.edulook.look.service;

import java.util.Optional;

import com.github.edulook.look.core.service.StudentService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.github.edulook.look.core.model.Student;
import com.github.edulook.look.core.repository.StudentRepository;

import lombok.AllArgsConstructor;

/**
 * Facade student service 
 */
@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository repository;

    @Cacheable("getStudentProfile")
    public Optional<Student> getStudentProfile(String studentId) {
        return repository
            .findStudentById(studentId);
    }
}

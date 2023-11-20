package com.github.edulook.look.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.github.edulook.look.core.model.Student;
import com.github.edulook.look.core.repository.StudentRepository;

import lombok.AllArgsConstructor;

/**
 * Facade student service 
 */
@Service
@AllArgsConstructor
public class StudentService {
    private final StudentRepository repository;

    public Optional<Student> getStudentProfile(String studentId) {
        return repository
            .findStudentById(studentId);
    }
}

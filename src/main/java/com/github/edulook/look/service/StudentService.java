package com.github.edulook.look.service;

import com.github.edulook.look.core.model.Student;
import com.github.edulook.look.core.repository.StudentRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    @Cacheable("getStudentProfile")
    public Optional<Student> getStudentProfile(String studentId) {
        return repository
            .findStudentById(studentId);
    }
}

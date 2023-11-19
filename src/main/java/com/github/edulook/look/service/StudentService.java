package com.github.edulook.look.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.github.edulook.look.core.model.Student;
import com.github.edulook.look.core.usecases.student.GetStudent;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
/**
 * Facade student service 
 */
public class StudentService {
    private final GetStudent getStudent;

    public Optional<Student> getStudentProfile(String studentId) {
        return getStudent
            .findStudentById(studentId);
    }
}

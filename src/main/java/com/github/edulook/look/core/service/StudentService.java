package com.github.edulook.look.core.service;

import com.github.edulook.look.core.model.Student;

import java.util.Optional;

public interface StudentService {
    Optional<Student> getStudentProfile(String studentId);
}

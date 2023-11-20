package com.github.edulook.look.core.repository.student;

import java.util.Optional;

import com.github.edulook.look.core.model.Student;

public interface GetStudent {
    Optional<Student> findStudentById(String studentId);
}

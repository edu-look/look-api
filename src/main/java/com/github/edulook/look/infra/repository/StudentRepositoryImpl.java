package com.github.edulook.look.infra.repository;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.github.edulook.look.core.model.Student;
import com.github.edulook.look.core.repository.StudentRepository;
import com.github.edulook.look.core.repository.student.GetStudent;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class StudentRepositoryImpl implements StudentRepository {

    private final GetStudent getStudent;

    @Override
    public Optional<Student> findStudentById(String studentId) {
        return getStudent.findStudentById(studentId);
    }
}

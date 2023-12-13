package com.github.edulook.look.infra.repository;

import com.github.edulook.look.core.model.Student;
import com.github.edulook.look.core.repository.StudentRepository;
import com.github.edulook.look.core.repository.student.GetStudent;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StudentRepositoryImpl implements StudentRepository {

    private final GetStudent getStudent;

    public StudentRepositoryImpl(GetStudent getStudent) {
        this.getStudent = getStudent;
    }

    @Override
    public Optional<Student> findStudentById(String studentId) {
        return getStudent.findStudentById(studentId);
    }
}

package com.github.edulook.look.infra.repository.db;

import com.github.edulook.look.core.model.Student;
import com.github.edulook.look.core.repository.StudentRepository;
import com.github.edulook.look.core.repository.student.GetStudent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("StudentRepositoryDB::Class")
public class StudentRepositoryDB implements StudentRepository {
    private final GetStudent getStudent;

    public StudentRepositoryDB(@Qualifier("GetStudentDB::Class") GetStudent getStudent) {
        this.getStudent = getStudent;
    }

    @Override
    public Optional<Student> findStudentById(String studentId) {
        return getStudent.findStudentById(studentId);
    }
}

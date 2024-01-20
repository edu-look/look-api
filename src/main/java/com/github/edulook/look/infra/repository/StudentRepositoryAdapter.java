package com.github.edulook.look.infra.repository;

import com.github.edulook.look.core.model.Student;
import com.github.edulook.look.core.repository.StudentRepository;
import com.github.edulook.look.core.repository.student.GetStudent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("StudentRepositoryAdapter::Class")
public class StudentRepositoryAdapter implements StudentRepository {
    private final StudentRepository db;
    private final StudentRepository http;

    public StudentRepositoryAdapter(@Lazy @Qualifier("StudentRepositoryDB::Class") StudentRepository db,
                                    @Lazy @Qualifier("StudentRepositoryHTTP::Class") StudentRepository http) {
        this.db = db;
        this.http = http;
    }

    @Override
    public Optional<Student> findStudentById(String studentId) {
        var student = db.findStudentById(studentId);
        if(student.isEmpty())
            return http.findStudentById(studentId);
        return student;
    }
}

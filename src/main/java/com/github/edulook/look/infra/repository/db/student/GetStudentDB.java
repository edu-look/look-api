package com.github.edulook.look.infra.repository.db.student;

import com.github.edulook.look.core.model.Student;
import com.github.edulook.look.core.repository.student.GetStudent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component("GetStudentDB::Class")
public class GetStudentDB implements GetStudent {
    @Override
    public Optional<Student> findStudentById(String studentId) {
        return Optional.empty();
    }
}

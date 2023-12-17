package com.github.edulook.look.infra.repository.student.storage;

import com.github.edulook.look.core.model.Student;
import com.github.edulook.look.core.repository.student.GetStudent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component("GetStudentStorage::Class")
public class GetStudentStorage implements GetStudent {
    @Override
    public Optional<Student> findStudentById(String studentId) {
        return Optional.empty();
    }
}

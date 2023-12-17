package com.github.edulook.look.infra.repository;

import com.github.edulook.look.core.model.Student;
import com.github.edulook.look.core.repository.StudentRepository;
import com.github.edulook.look.core.repository.student.GetStudent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StudentRepositoryAdapter implements StudentRepository {

    private final GetStudent getStudentClassroom;
    private final GetStudent getGetStudentStorage;

    public StudentRepositoryAdapter(@Qualifier("GetStudentClassroom::Class") GetStudent getStudentClassroom,
                                    @Qualifier("GetStudentStorage::Class") GetStudent getGetStudentStorage) {
        this.getStudentClassroom = getStudentClassroom;
        this.getGetStudentStorage = getGetStudentStorage;
    }


    @Override
    public Optional<Student> findStudentById(String studentId) {
        var student = getGetStudentStorage.findStudentById(studentId);

        if(student.isEmpty())
            return getStudentClassroom.findStudentById(studentId);

        return student;
    }
}

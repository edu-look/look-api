package com.github.edulook.look.infra.repository.student.classroom;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.github.edulook.look.core.model.Student;
import com.github.edulook.look.core.repository.student.GetStudent;
import com.github.edulook.look.infra.repository.student.classroom.mapper.ClassroomStudentAndCoreStudentMapper;
import com.google.api.services.classroom.Classroom;
import com.google.api.services.classroom.model.UserProfile;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("GetStudentClassroom::Class")
public class GetStudentClassroom implements GetStudent {

    private final Classroom classroom;
    private final ClassroomStudentAndCoreStudentMapper mapper;

    public GetStudentClassroom(Classroom classroom, ClassroomStudentAndCoreStudentMapper mapper) {
        this.classroom = classroom;
        this.mapper = mapper;
    }

    @Override
    public Optional<Student> findStudentById(String studentId) {
        try{
            UserProfile userProfile = classroom
                .userProfiles()
                .get(studentId)
                .execute();
            
            return Optional.of(mapper.toModel(userProfile));
            
        } catch (IOException e) {
            log.error("error:: ", e);
            return Optional.empty();
        }
    }
}

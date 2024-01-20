package com.github.edulook.look.infra.repository.http.student;

import com.github.edulook.look.core.model.Student;
import com.github.edulook.look.core.repository.student.GetStudent;
import com.github.edulook.look.infra.repository.http.student.mapper.ClassroomStudentAndCoreStudentMapper;
import com.google.api.services.classroom.Classroom;
import com.google.api.services.classroom.model.UserProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;


@Slf4j
@Component("GetStudentHTTP::Class")
public class GetStudentHTTP implements GetStudent {
    private final Classroom classroom;
    private final ClassroomStudentAndCoreStudentMapper mapper;

    public GetStudentHTTP(Classroom classroom, ClassroomStudentAndCoreStudentMapper mapper) {
        this.classroom = classroom;
        this.mapper = mapper;
    }

    @Override
    public Optional<Student> findStudentById(String studentId) {
        try{
            var userProfile = classroom
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

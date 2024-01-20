package com.github.edulook.look.infra.repository.http.student.mapper;

import com.github.edulook.look.core.model.Student;
import com.github.edulook.look.core.model.Student.Profile;
import com.google.api.services.classroom.model.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class ClassroomStudentAndCoreStudentMapper {
    public Student toModel(UserProfile source) {
        return Student
            .builder()
            .id(source.getId())
            .profile(Profile
                .builder()
                .email(source.getEmailAddress())
                .name(source.getName().getFullName())
                .photoUrl(source.getPhotoUrl())
                .build()
            )
            .build();
    }
}

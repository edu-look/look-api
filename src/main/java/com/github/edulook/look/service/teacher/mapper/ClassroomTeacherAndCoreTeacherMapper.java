package com.github.edulook.look.service.teacher.mapper;

import org.springframework.stereotype.Component;

import com.github.edulook.look.core.model.Teacher;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ClassroomTeacherAndCoreTeacherMapper {
    
    public Teacher toModel(com.google.api.services.classroom.model.Teacher source) {
        return Teacher
            .builder()
            .id(source.getUserId())
            .name(source.getProfile().getName().getFullName())
            .photo(source.getProfile().getPhotoUrl())
            .email(source.getProfile().getEmailAddress())
            .build();
    }
}

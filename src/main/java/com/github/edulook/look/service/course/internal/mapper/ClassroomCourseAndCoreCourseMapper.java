package com.github.edulook.look.service.course.internal.mapper;

import org.springframework.stereotype.Component;

import com.github.edulook.look.core.model.Course;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ClassroomCourseAndCoreCourseMapper {

    public Course toModel(com.google.api.services.classroom.model.Course source) {
        return Course
            .builder()
            .id(source.getId())
            .state(source.getCourseState())
            .description(source.getDescription())
            .name(source.getName())
            .ownerId(source.getOwnerId())
            .room(source.getRoom())
            .section(source.getSection())
            .updated(source.getUpdateTime())
            .build();
    }
}

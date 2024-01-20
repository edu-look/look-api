package com.github.edulook.look.infra.repository.http.course.mapper;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.model.Teacher;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@NoArgsConstructor
public class ClassroomCourseToCoreCourseMapper {

    public Course toModel(com.google.api.services.classroom.model.Course source) {
        return toModel(source, Map.of());
    }

    public Course toModel(com.google.api.services.classroom.model.Course source, Map<String, List<Teacher>> teachers) {        
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
            .teachers(teachers.getOrDefault(source.getId(), List.of()))
            .build();
    }

    public Course toModel(com.google.api.services.classroom.model.Course source, List<Teacher> teachers) {        
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
            .teachers(teachers)
            .build();
    }
}

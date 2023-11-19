package com.github.edulook.look.service.teacher;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.github.edulook.look.core.model.Teacher;
import com.github.edulook.look.core.usecases.teacher.GetTeacher;
import com.github.edulook.look.service.teacher.mapper.ClassroomTeacherAndCoreTeacherMapper;
import com.google.api.services.classroom.Classroom;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class GetTeacherImpl implements GetTeacher {

    private final Classroom classroom;
    private final ClassroomTeacherAndCoreTeacherMapper mapper;

    @Override
    public List<Teacher> getTeachersFromCourse(String courseId) throws IOException {
        return classroom.courses()
            .teachers()
            .list(courseId)
            .execute()
            .getTeachers()
            .stream()
            .map(it -> mapper.toModel(it))
            .collect(Collectors.toList());
    }
}

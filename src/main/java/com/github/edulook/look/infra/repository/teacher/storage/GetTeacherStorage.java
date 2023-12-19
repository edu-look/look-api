package com.github.edulook.look.infra.repository.teacher.storage;

import com.github.edulook.look.core.model.Teacher;
import com.github.edulook.look.core.repository.teacher.GetTeacher;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component("GetTeacherStorage::Class")
public class GetTeacherStorage implements GetTeacher {
    @Override
    public List<Teacher> getTeachersFromCourse(String courseId) throws IOException {
        return List.of();
    }

    @Override
    public Optional<Teacher> getTeacherFromCourseById(String courseId, String teacherId) {
        return Optional.empty();
    }
}

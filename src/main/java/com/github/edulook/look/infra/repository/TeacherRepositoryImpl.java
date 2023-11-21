package com.github.edulook.look.infra.repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.github.edulook.look.core.model.Teacher;
import com.github.edulook.look.core.repository.TeacherRepository;
import com.github.edulook.look.core.repository.teacher.GetTeacher;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TeacherRepositoryImpl implements TeacherRepository {

    @Autowired
    @Qualifier("GetTeacher::Class")
    private GetTeacher getTeacher;

    @Override
    public List<Teacher> getTeachersFromCourse(String courseId) throws IOException {
        return getTeacher.getTeachersFromCourse(courseId);
    }

    @Override
    public Optional<Teacher> getTeacherFromCourseById(String courseId, String teacherId) {
        return getTeacher.getTeacherFromCourseById(courseId, teacherId);
    }
}

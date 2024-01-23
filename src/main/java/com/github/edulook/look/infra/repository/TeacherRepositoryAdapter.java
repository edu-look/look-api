package com.github.edulook.look.infra.repository;

import com.github.edulook.look.core.model.Teacher;
import com.github.edulook.look.core.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component("TeacherRepositoryAdapter::Class")
public class TeacherRepositoryAdapter implements TeacherRepository {
    private final TeacherRepository db;
    private final TeacherRepository http;

    public TeacherRepositoryAdapter(@Qualifier("TeacherRepositoryDB::Class") TeacherRepository db,
                                    @Qualifier("TeacherRepositoryHTTP::Class") TeacherRepository http) {
        this.db = db;
        this.http = http;
    }

    @Override
    public List<Teacher> getTeachersFromCourse(String courseId) throws IOException {
        var teaches = db.getTeachersFromCourse(courseId);
        if(teaches.isEmpty())
            return http.getTeachersFromCourse(courseId);
        return teaches;
    }

    @Override
    public Optional<Teacher> getTeacherFromCourseById(String courseId, String teacherId) {
        var teacher = db.getTeacherFromCourseById(courseId, teacherId);
        if(teacher.isEmpty())
            return http.getTeacherFromCourseById(courseId, teacherId);
        return teacher;
    }
}

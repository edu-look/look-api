package com.github.edulook.look.infra.repository.http;

import com.github.edulook.look.core.model.Teacher;
import com.github.edulook.look.core.repository.TeacherRepository;
import com.github.edulook.look.core.repository.teacher.GetTeacher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component("TeacherRepositoryHTTP::Class")
public class TeacherRepositoryHTTP implements TeacherRepository {

    private final GetTeacher getTeacher;

    public TeacherRepositoryHTTP(@Qualifier("GetTeacherHTTP::Class") GetTeacher getTeacher) {
        this.getTeacher = getTeacher;
    }

    @Override
    public List<Teacher> getTeachersFromCourse(String courseId) throws IOException {
        return getTeacher.getTeachersFromCourse(courseId);
    }

    @Override
    public Optional<Teacher> getTeacherFromCourseById(String courseId, String teacherId) {
        return getTeacher.getTeacherFromCourseById(courseId, teacherId);
    }
}

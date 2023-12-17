package com.github.edulook.look.infra.repository;

import com.github.edulook.look.core.model.Teacher;
import com.github.edulook.look.core.repository.TeacherRepository;
import com.github.edulook.look.core.repository.teacher.GetTeacher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class TeacherRepositoryAdapter implements TeacherRepository {

    private final GetTeacher getTeacherClassroom;
    private final GetTeacher getTeacherStorage;

    public TeacherRepositoryAdapter(@Qualifier("GetTeacherClassroom::Class") GetTeacher getTeacherClassroom,
                                    @Qualifier("GetTeacherStorage::Class") GetTeacher getTeacherStorage) {
        this.getTeacherClassroom = getTeacherClassroom;
        this.getTeacherStorage = getTeacherStorage;
    }

    @Override
    public List<Teacher> getTeachersFromCourse(String courseId) throws IOException {
        var teaches = getTeacherStorage.getTeachersFromCourse(courseId);

        if(teaches.isEmpty())
            return getTeacherClassroom.getTeachersFromCourse(courseId);

        return teaches;
    }

    @Override
    public Optional<Teacher> getTeacherFromCourseById(String courseId, String teacherId) {
        var teacher = getTeacherStorage.getTeacherFromCourseById(courseId, teacherId);

        if(teacher.isEmpty())
            return getTeacherClassroom.getTeacherFromCourseById(courseId, teacherId);

        return teacher;
    }
}

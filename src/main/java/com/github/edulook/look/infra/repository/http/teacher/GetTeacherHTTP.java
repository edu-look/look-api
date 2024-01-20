package com.github.edulook.look.infra.repository.http.teacher;

import com.github.edulook.look.core.model.Teacher;
import com.github.edulook.look.core.repository.teacher.GetTeacher;
import com.github.edulook.look.infra.repository.http.teacher.mapper.ClassroomTeacherAndCoreTeacherMapper;
import com.google.api.services.classroom.Classroom;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Component("GetTeacherHTTP::Class")
public class GetTeacherHTTP implements GetTeacher {
    private final Classroom classroom;
    private final ClassroomTeacherAndCoreTeacherMapper mapper;

    public GetTeacherHTTP(Classroom classroom, ClassroomTeacherAndCoreTeacherMapper mapper) {
        this.classroom = classroom;
        this.mapper = mapper;
    }

    @Override
    public List<Teacher> getTeachersFromCourse(String courseId) throws IOException {
        return toCoreTeacher(getClassroomTeacherByCourse(courseId));
    }

    private List<Teacher> toCoreTeacher(List<com.google.api.services.classroom.model.Teacher> teachers) {
        return teachers.parallelStream()
            .map(mapper::toModel)
            .toList();
    }

    private List<com.google.api.services.classroom.model.Teacher> getClassroomTeacherByCourse(String courseId) throws IOException {
        return classroom.courses()
            .teachers()
            .list(courseId)
            .execute()
            .getTeachers();
    }

    @Override
    public Optional<Teacher> getTeacherFromCourseById(String courseId, String teacherId) {
       try {
            var teacher = classroom
                .courses()
                .teachers()
                .get(courseId, teacherId)
                .execute();
            return Optional.ofNullable(mapper.toModel(teacher));
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}

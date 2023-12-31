package com.github.edulook.look.infra.repository.teacher.classroom;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.github.edulook.look.core.model.Teacher;
import com.github.edulook.look.core.repository.teacher.GetTeacher;
import com.github.edulook.look.infra.repository.teacher.classroom.mapper.ClassroomTeacherAndCoreTeacherMapper;
import com.google.api.services.classroom.Classroom;


@Component("GetTeacherClassroom::Class")
public class GetTeacherClassroom implements GetTeacher {

    private final Classroom classroom;
    private final ClassroomTeacherAndCoreTeacherMapper mapper;

    public GetTeacherClassroom(Classroom classroom, ClassroomTeacherAndCoreTeacherMapper mapper) {
        this.classroom = classroom;
        this.mapper = mapper;
    }

    @Override
    public List<Teacher> getTeachersFromCourse(String courseId) throws IOException {
        var teachers = getClassroomTeacherByCourse(courseId);
        return toCoreTeacher(teachers);
    }

    private List<Teacher> toCoreTeacher(List<com.google.api.services.classroom.model.Teacher> teachers) {
        return teachers
            .stream()
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

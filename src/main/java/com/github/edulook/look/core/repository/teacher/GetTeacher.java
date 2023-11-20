package com.github.edulook.look.core.repository.teacher;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.github.edulook.look.core.model.Teacher;

public interface GetTeacher {
    List<Teacher> getTeachersFromCourse(String courseId) throws IOException;
    Optional<Teacher> getTeacherFromCourseById(String courseId, String teacherId);
}

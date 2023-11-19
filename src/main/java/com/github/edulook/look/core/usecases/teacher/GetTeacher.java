package com.github.edulook.look.core.usecases.teacher;

import java.io.IOException;
import java.util.List;

import com.github.edulook.look.core.model.Teacher;

public interface GetTeacher {
    List<Teacher> getTeachersFromCourse(String courseId) throws IOException;
}

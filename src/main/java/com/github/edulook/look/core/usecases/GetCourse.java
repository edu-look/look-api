package com.github.edulook.look.core.usecases;

import java.util.List;
import java.util.Optional;

import com.github.edulook.look.core.model.Course;

public interface GetCourse {
    List<Course> findCoursesByStudentId(String studentId);
    Optional<Course> findOneCourseByStudentId(String courseId, String studentId);
}

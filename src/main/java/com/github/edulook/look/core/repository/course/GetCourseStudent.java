package com.github.edulook.look.core.repository.course;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.model.CourseStudent;

import java.util.List;
import java.util.Optional;

public interface GetCourseStudent {
    Optional<CourseStudent> findCoursesByStudentId(String studentId);
    Optional<CourseStudent> findOneCourseByStudentId(String courseId, String studentId);

    List<Course> findAllCourses(String studentId);
}

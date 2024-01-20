package com.github.edulook.look.infra.repository.db.course;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.repository.course.GetCourse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component("GetCourseDB::Class")
public class GetCourseDB implements GetCourse {
    @Override
    public List<Course> findCoursesByStudentId(String studentId) {
        return List.of();
    }

    @Override
    public Optional<Course> findOneCourseByStudentId(String courseId, String studentId) {
        return Optional.empty();
    }
}

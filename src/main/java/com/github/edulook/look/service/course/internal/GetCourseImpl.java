package com.github.edulook.look.service.course.internal;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.github.edulook.look.core.data.CourseState;
import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.usecases.GetCourse;
import com.github.edulook.look.service.course.internal.mapper.ClassroomCourseAndCoreCourseMapper;
import com.google.api.services.classroom.Classroom;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Component
@Slf4j
public class GetCourseImpl implements GetCourse {

    private final Classroom classroom;
    private final ClassroomCourseAndCoreCourseMapper mapper;

    @Override
    public List<Course> findCoursesByStudentId(String studentId) {
        try {
            var clientRequest = classroom.courses().list()
                .setStudentId(studentId)
                .setCourseStates(List.of(CourseState.ACTIVE))
                .execute();

            return Optional.ofNullable(clientRequest.getCourses())
                .orElse(List.of())
                .stream()
                .map(mapper::toModel)
                .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
            log.error("studentId: {}: {}", studentId, e);
            return List.of();
        }
    }

    @Override
    public Optional<Course> findOneCourseByStudentId(String courseId, String studentId) {
        throw new UnsupportedOperationException("Unimplemented method 'findCourseByStudentId'");
    }
}

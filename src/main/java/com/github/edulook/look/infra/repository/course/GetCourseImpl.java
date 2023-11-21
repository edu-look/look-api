package com.github.edulook.look.infra.repository.course;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.github.edulook.look.core.data.CourseState;
import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.model.Teacher;
import com.github.edulook.look.core.repository.course.GetCourse;
import com.github.edulook.look.core.repository.teacher.GetTeacher;
import com.github.edulook.look.infra.repository.course.mapper.ClassroomCourseAndCoreCourseMapper;
import com.google.api.services.classroom.Classroom;
import com.google.api.services.classroom.model.ListCoursesResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("GetCourse::Class")
public class GetCourseImpl implements GetCourse {

    @Autowired
    private Classroom classroom;
    
    @Autowired
    private ClassroomCourseAndCoreCourseMapper mapper;

    @Autowired
    @Qualifier("GetTeacher::Class")
    private GetTeacher getTeacher;

    @Override
    public List<Course> findCoursesByStudentId(String studentId) {
        try {
            var courses = findCourses(studentId)
                .getCourses();

            var teachers = getTeachersByCourse(getCourseIDs(courses));
            
            return transformClassroomCourseToCoreCourse(courses, teachers);

        } catch (IOException e) {
            e.printStackTrace();
            log.error("studentId: {}: {}", studentId, e);
            return List.of();
        }
    }

    private List<Course> transformClassroomCourseToCoreCourse(List<com.google.api.services.classroom.model.Course> courses, Map<String, List<Teacher>> teachers) {
        return Optional.ofNullable(courses)
            .orElse(List.of())
            .stream()
            .map(it -> mapper.toModel(it, teachers))
            .collect(Collectors.toList());
    }

    private List<String> getCourseIDs(List<com.google.api.services.classroom.model.Course> courses) {
        return courses
            .stream()
            .map(it -> it.getId()).toList();
    }

    private ListCoursesResponse findCourses(String studentId) throws IOException {
        var clientRequest = classroom.courses()
            .list()
            .setStudentId(studentId)
            .setCourseStates(List.of(CourseState.ACTIVE))
            .execute();
        return clientRequest;
    }

    private Map<String, List<Teacher>> getTeachersByCourse(List<String> courseIdList) throws IOException {
        var teachersByCourse = new HashMap<String, List<Teacher>>();

        for(var courseId : courseIdList) {
            teachersByCourse.putIfAbsent(courseId, getTeacher.getTeachersFromCourse(courseId));
        }

        return teachersByCourse;
    }

    @Override
    public Optional<Course> findOneCourseByStudentId(String courseId, String studentId) {

        try {
            var courseFound = getClassroomCourse(courseId);
            if(courseFound.isEmpty()) {
                return Optional.empty();
            }

            var course = courseFound.get();
            var teachers = getTeacher.getTeachersFromCourse(course.getId());
            
            return Optional.ofNullable(mapper.toModel(course, teachers));

        } catch (IOException e) {
            e.printStackTrace();
            log.error("findCourse error: {}", e);
        }

        return Optional.empty();
    }

    private Optional<com.google.api.services.classroom.model.Course> getClassroomCourse(String courseId) throws IOException {
        return Optional.ofNullable(classroom.courses()
            .get(courseId)
            .execute()
        );
    }
}

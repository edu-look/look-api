package com.github.edulook.look.infra.repository.http.course;

import com.github.edulook.look.core.data.CourseState;
import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.model.Teacher;
import com.github.edulook.look.core.repository.course.GetCourse;
import com.github.edulook.look.core.repository.teacher.GetTeacher;
import com.github.edulook.look.infra.repository.http.course.mapper.ClassroomCourseToCoreCourseMapper;
import com.google.api.services.classroom.Classroom;
import com.google.api.services.classroom.model.ListCoursesResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component("GetCourseHTTP::Class")
public class GetCourseHTTP implements GetCourse {
    private final Classroom classroom;
    private final ClassroomCourseToCoreCourseMapper mapper;
    private final GetTeacher getTeacher;

    public GetCourseHTTP(Classroom classroom, ClassroomCourseToCoreCourseMapper mapper,
                         @Qualifier("GetTeacherHTTP::Class") GetTeacher getTeacher) {
        this.classroom = classroom;
        this.mapper = mapper;
        this.getTeacher = getTeacher;
    }

    @Override
    public List<Course> findCoursesByStudentId(String studentId) {
        try {
            var response = findCourses(studentId);
            if(response.isEmpty())
                return  List.of();

            var courses =  response.get().getCourses();
            var teachers = getTeachersByCourse(getCourseIDs(courses));
            
            return transformClassroomCourseToCoreCourse(courses, teachers);

        } catch (IOException e) {
            log.error("error:: ", e);
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
            .map(com.google.api.services.classroom.model.Course::getId)
            .toList();
    }

    private Optional<ListCoursesResponse> findCourses(String studentId) throws IOException {
        var courses = classroom.courses()
            .list()
            .setStudentId(studentId)
            .setCourseStates(List.of(CourseState.ACTIVE))
            .execute();

        if(courses == null || courses.isEmpty())
            return Optional.empty();

        return Optional.of(courses);
    }

    private Optional<ListCoursesResponse> findCoursesTeacher(String teacherId) throws IOException {
        var courses = classroom.courses()
                .list()
                .setTeacherId(teacherId)
                .setCourseStates(List.of(CourseState.ACTIVE))
                .execute();

        if(courses == null || courses.isEmpty())
            return Optional.empty();

        return Optional.of(courses);
    }

    private Map<String, List<Teacher>> getTeachersByCourse(List<String> courseIdList) throws IOException {
        var teachersByCourse = new HashMap<String, List<Teacher>>();
        /*
          TODO
          It need to resolve this
          N + 1 Problem: https://planetscale.com/blog/what-is-n-1-query-problem-and-how-to-solve-it
         */
        for(var courseId : courseIdList)
            teachersByCourse.putIfAbsent(courseId, getTeacher.getTeachersFromCourse(courseId));

        return teachersByCourse;
    }

    @Override
    public Optional<Course> findOneCourseByStudentId(String courseId, String studentId) {
        try {
            var courseFound = getClassroomCourse(courseId);
            if(courseFound.isEmpty())
                return Optional.empty();
            var course = courseFound.get();
            var teachers = getTeacher.getTeachersFromCourse(course.getId());
            return Optional.ofNullable(mapper.toModel(course, teachers));
        } catch (IOException e) {
            log.error("error:: ", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Course> findCoursesByTeacherID(String teacherId) {
        try {
            var response = findCoursesTeacher(teacherId);
            if(response.isEmpty())
                return  List.of();

            var courses =  response.get().getCourses();
            var teachers = getTeachersByCourse(getCourseIDs(courses));

            return transformClassroomCourseToCoreCourse(courses, teachers);

        } catch (IOException e) {
            log.error("error:: ", e);
            return List.of();
        }
    }

    @Override
    public Optional<Course> findOneCourseByTeacherID(String courseId, String teacherId) {
        try {
            var courseFound = getClassroomCourse(courseId);
            if(courseFound.isEmpty())
                return Optional.empty();
            var course = courseFound.get();
            var teachers = getTeacher.getTeachersFromCourse(course.getId());
            return Optional.ofNullable(mapper.toModel(course, teachers));
        } catch (IOException e) {
            log.error("error:: ", e);
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

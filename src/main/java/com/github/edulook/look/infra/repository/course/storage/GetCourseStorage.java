package com.github.edulook.look.infra.repository.course.storage;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.model.CourseStudent;
import com.github.edulook.look.core.repository.course.GetCourse;
import com.github.edulook.look.core.repository.course.GetCourseStudent;
import com.github.edulook.look.infra.repository.firestore.CourseFirestoreRepository;
import com.github.edulook.look.infra.repository.firestore.CourseStudentFirestoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component("GetCourseStorage::Class")
public class GetCourseStorage implements GetCourseStudent {

    private final CourseStudentFirestoreRepository courseStudentFirestoreRepository;

    private final CourseFirestoreRepository courseFirestore;

    public GetCourseStorage(CourseStudentFirestoreRepository courseStudentFirestoreRepository, CourseFirestoreRepository courseFirestore) {
        this.courseStudentFirestoreRepository = courseStudentFirestoreRepository;
        this.courseFirestore = courseFirestore;
    }

    @Override
    public Optional<CourseStudent> findCoursesByStudentId(String studentId) {
        return Optional.ofNullable(courseStudentFirestoreRepository.findById(studentId).blockOptional()
                .orElseThrow(() -> new RuntimeException("Student course not found for ID: " + studentId)));
    }

    @Override
    public Optional<CourseStudent> findOneCourseByStudentId(String courseId, String studentId) {
        return Optional.ofNullable(courseStudentFirestoreRepository.findOneCourseByStudentId(courseId, studentId)
                .orElseThrow(() -> new RuntimeException("Student course not found for ID: " + studentId)));
    }

    @Override
    public List<Course> findAllCourses(String studentId) {
        return null;
    }

//    @Override
//    public List<Course> findCoursesByStudentId(String studentId) {
//        //1 - obter os cursos da collections look_course_student
//        //2 - Buscar os cursos por Id do estudante
//        //3 - Retornar a lista de cursos
//        return courseFirestore.findAll().collectList().block();
//    }
//
//    @Override
//    public Optional<Course> findOneCourseByStudentId(String courseId, String studentId) {
//        return Optional.empty();
//    }
}

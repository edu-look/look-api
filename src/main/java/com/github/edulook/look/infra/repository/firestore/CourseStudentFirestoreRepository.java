package com.github.edulook.look.infra.repository.firestore;

import com.github.edulook.look.core.model.CourseStudent;
import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseStudentFirestoreRepository extends FirestoreReactiveRepository<CourseStudent> {

    @Query(value = "select * from look_student_course where courseId = :courseId and studentId = :studentId", nativeQuery = true)
    Optional<CourseStudent> findOneCourseByStudentId(@Param("courseId") String courseId, @Param("studentId") String studentId);

}

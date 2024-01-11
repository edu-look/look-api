//package com.github.edulook.look.infra.repository.postgresql;
//
//import com.github.edulook.look.core.model.CourseStudent;
//import org.springframework.context.annotation.Bean;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//@EnableJpaRepositories
//public interface CourseStudentPostgresqlRepository extends JpaRepository<CourseStudent, String> {
//
//    @Query(value = "select * from look_student_course where courseId = :courseId and studentId = :studentId", nativeQuery = true)
//    Optional<CourseStudent> findOneCourseByStudentId(@Param("courseId") String courseId, @Param("studentId") String studentId);
//
//}

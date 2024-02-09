package com.github.edulook.look.infra.repository.db.course.config;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.model.WorkMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseWorkMaterialJPA extends JpaRepository<WorkMaterial, String> {

    @Query("SELECT wm FROM WorkMaterial wm WHERE wm.courseId = :courseId AND wm.access = :access")
    List<WorkMaterial> findAllByCourseAndAccess(Course courseId, String access);

    @Query("SELECT wm FROM WorkMaterial wm WHERE wm.courseId = :courseId")
    List<WorkMaterial> findAllByCourse(Course courseId);

    @Query("SELECT wm FROM WorkMaterial wm WHERE wm.courseId = :courseId AND wm.id = :materialId")
    Optional<WorkMaterial> findByCourseAndMaterialId(Course courseId, String materialId);



}

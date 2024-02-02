package com.github.edulook.look.core.repository.course;

import java.util.List;
import java.util.Optional;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.model.WorkMaterial;

public interface GetCourseWorkMaterial {
    List<WorkMaterial> listAllWorkMaterial(Course course, String access);
    List<WorkMaterial> listAllWorkMaterial(Course course);

    Optional<WorkMaterial> findOneMaterial(Course course, String materialId);
}

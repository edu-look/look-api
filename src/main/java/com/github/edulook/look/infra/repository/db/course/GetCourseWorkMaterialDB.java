package com.github.edulook.look.infra.repository.db.course;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.model.WorkMaterial;
import com.github.edulook.look.core.repository.course.GetCourseWorkMaterial;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component("GetCourseWorkMaterialDB::Class")
public class GetCourseWorkMaterialDB implements GetCourseWorkMaterial {
    @Override
    public List<WorkMaterial> listAllWorkMaterial(Course course, String access) {
        return List.of();
    }

    @Override
    public List<WorkMaterial> listAllWorkMaterial(Course course) {
        return List.of();
    }

    @Override
    public Optional<WorkMaterial> findOneMaterial(Course course, String materialId) {
        return Optional.empty();
    }
}

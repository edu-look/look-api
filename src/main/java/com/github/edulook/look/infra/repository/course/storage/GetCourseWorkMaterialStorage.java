package com.github.edulook.look.infra.repository.course.storage;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.repository.course.GetCourseWorkMaterial;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component("GetCourseWorkMaterialStorage::Class")
public class GetCourseWorkMaterialStorage implements GetCourseWorkMaterial {
    @Override
    public List<Course.WorkMaterial> listAllWorkMaterial(Course course, String access) {
        return List.of();
    }

    @Override
    public List<Course.WorkMaterial> listAllWorkMaterial(Course course) {
        return List.of();
    }

    @Override
    public Optional<Course.WorkMaterial> findOneMaterial(Course course, String materialId) {
        return Optional.empty();
    }
}

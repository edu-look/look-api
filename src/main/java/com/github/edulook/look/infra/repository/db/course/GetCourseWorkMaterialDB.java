package com.github.edulook.look.infra.repository.db.course;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.model.WorkMaterial;
import com.github.edulook.look.core.repository.course.GetCourseWorkMaterial;
import com.github.edulook.look.infra.repository.db.course.config.CourseWorkMaterialJPA;
import com.github.edulook.look.infra.repository.db.course.config.UpsetCourseWorkMaterialJPA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component("GetCourseWorkMaterialDB::Class")
public class GetCourseWorkMaterialDB implements GetCourseWorkMaterial {

    private final CourseWorkMaterialJPA repository;

    public GetCourseWorkMaterialDB(CourseWorkMaterialJPA repository) {
        this.repository = repository;
    }

    @Override
    public List<WorkMaterial> listAllWorkMaterial(Course course, String access) {
        return repository.findAllByCourseAndAccess(course, access);
    }

    @Override
    public List<WorkMaterial> listAllWorkMaterial(Course course) {
        return repository.findAllByCourse(course);
    }

    @Override
    public Optional<WorkMaterial> findOneMaterial(Course course, String materialId) {
        return repository.findByCourseAndMaterialId(course, materialId);
    }
}

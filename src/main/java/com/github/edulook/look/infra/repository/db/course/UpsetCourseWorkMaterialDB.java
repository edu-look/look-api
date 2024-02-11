package com.github.edulook.look.infra.repository.db.course;

import com.github.edulook.look.core.model.WorkMaterial;
import com.github.edulook.look.core.repository.course.UpsetCourseWorkMaterial;
import com.github.edulook.look.infra.repository.db.course.config.UpsetCourseWorkMaterialJPA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("UpsetCourseWorkMaterialDB::Class")
public class UpsetCourseWorkMaterialDB implements UpsetCourseWorkMaterial {
    private final UpsetCourseWorkMaterialJPA repository;

    public UpsetCourseWorkMaterialDB(UpsetCourseWorkMaterialJPA repository) {
        this.repository = repository;
    }

    @Override
    public WorkMaterial upsetCourseMaterial(WorkMaterial materialSaved) {
        return repository.save(materialSaved);
    }
}

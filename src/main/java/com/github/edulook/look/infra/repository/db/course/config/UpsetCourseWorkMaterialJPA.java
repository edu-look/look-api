package com.github.edulook.look.infra.repository.db.course.config;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.edulook.look.core.model.WorkMaterial;

@Repository
public interface UpsetCourseWorkMaterialJPA extends JpaRepository<WorkMaterial, String> {
}

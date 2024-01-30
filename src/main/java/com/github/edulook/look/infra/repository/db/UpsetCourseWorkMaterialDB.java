package com.github.edulook.look.infra.repository.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import static com.github.edulook.look.core.model.Course.WorkMaterial;

@Repository
public interface UpsetCourseWorkMaterialDB extends JpaRepository<WorkMaterial, String> {

}

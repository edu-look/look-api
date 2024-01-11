package com.github.edulook.look.infra.repository.postgresql;

import com.github.edulook.look.core.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkMaterialPostgresqlRepository extends JpaRepository<Course.WorkMaterial, String> {
}

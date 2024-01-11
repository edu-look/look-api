package com.github.edulook.look.infra.repository.postgresql;

import com.github.edulook.look.core.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CoursePostgresqlRepository extends JpaRepository<Course, String>{
}

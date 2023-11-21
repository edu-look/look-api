package com.github.edulook.look.core.repository.course;

import java.util.List;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.model.Course.WorkMaterial;

public interface GetCourseWorkMaterial {
    List<WorkMaterial> listAllWorkMaterial(Course course, String access);
    List<WorkMaterial> listAllWorkMaterial(Course course);
}

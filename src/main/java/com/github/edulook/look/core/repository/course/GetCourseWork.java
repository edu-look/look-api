package com.github.edulook.look.core.repository.course;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.model.Course.WorkMaterial;

import java.util.List;

public interface GetCourseWork {
    List<WorkMaterial> listAllWorks(Course course);
}

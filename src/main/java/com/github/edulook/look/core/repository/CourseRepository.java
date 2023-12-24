package com.github.edulook.look.core.repository;

import static com.github.edulook.look.core.model.Course.WorkMaterial;

import com.github.edulook.look.core.repository.course.*;

public interface CourseRepository
        extends GetCourse,
        GetCourseWorkMaterial,
        GetCourseWork,
        GetCourseAnnouncement,
        UpsetCourseWorkMaterial {
}

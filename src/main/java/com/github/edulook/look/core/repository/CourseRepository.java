package com.github.edulook.look.core.repository;

import static com.github.edulook.look.core.model.Course.WorkMaterial;
import com.github.edulook.look.core.repository.course.GetCourse;
import com.github.edulook.look.core.repository.course.GetCourseAnnouncement;
import com.github.edulook.look.core.repository.course.GetCourseWorkMaterial;
import com.github.edulook.look.core.repository.course.UpsetCourseWorkMaterial;

public interface CourseRepository
        extends GetCourse,
        GetCourseWorkMaterial,
        GetCourseAnnouncement,
        UpsetCourseWorkMaterial {
}

package com.github.edulook.look.core.repository;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.repository.course.*;


public interface CourseRepository
        extends GetCourse,
        GetCourseWorkMaterial,
        GetCourseWork,
        GetCourseAnnouncement,
        UpsetCourseWorkMaterial,
        Crud<Course> {
}

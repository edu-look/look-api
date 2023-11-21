package com.github.edulook.look.core.repository;

import com.github.edulook.look.core.repository.course.GetCourse;
import com.github.edulook.look.core.repository.course.GetCourseAnnouncement;
import com.github.edulook.look.core.repository.course.GetCourseWorkMaterial;

public interface CourseRepository extends GetCourse, GetCourseWorkMaterial, GetCourseAnnouncement {
}

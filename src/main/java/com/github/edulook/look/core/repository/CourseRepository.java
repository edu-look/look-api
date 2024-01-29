package com.github.edulook.look.core.repository;

import static com.github.edulook.look.core.model.Course.WorkMaterial;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.repository.course.*;

import java.util.List;
import java.util.Optional;

public interface CourseRepository
        extends GetCourse,
        GetCourseWorkMaterial,
        GetCourseWork,
        GetCourseAnnouncement,
        UpsetCourseWorkMaterial {

}

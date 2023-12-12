package com.github.edulook.look.core.service;

import com.github.edulook.look.core.model.Course;

import java.io.IOException;
import java.util.List;

public interface CourseService {
    List<Course> listCourses(String studentId) throws IOException;
    List<Course.WorkMaterial> listAllWorkMaterials(String courseId, String access);
    List<Course.Announcement> listAllAnnouncements(String courseId, String studentId);
}

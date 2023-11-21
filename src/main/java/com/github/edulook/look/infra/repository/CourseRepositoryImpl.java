package com.github.edulook.look.infra.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.model.Course.Announcement;
import com.github.edulook.look.core.model.Course.WorkMaterial;
import com.github.edulook.look.core.repository.CourseRepository;
import com.github.edulook.look.core.repository.course.GetCourse;
import com.github.edulook.look.core.repository.course.GetCourseAnnouncement;
import com.github.edulook.look.core.repository.course.GetCourseWorkMaterial;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CourseRepositoryImpl implements CourseRepository {
    
    @Autowired
    @Qualifier("GetCourse::Class")
    private GetCourse getCourse;
   
    @Autowired
    @Qualifier("GetCourseWorkMaterial::Class")
    private GetCourseWorkMaterial getCourseWorkMaterial;

    @Autowired
    @Qualifier("GetTeacherAnnouncement::class")
    private GetCourseAnnouncement announcements;


    @Override
    public List<Course> findCoursesByStudentId(String studentId) {
       return getCourse.findCoursesByStudentId(studentId);
    }

    @Override
    public Optional<Course> findOneCourseByStudentId(String courseId, String studentId) {
       return getCourse.findOneCourseByStudentId(courseId, studentId);
    }

    @Override
    public List<WorkMaterial> listAllWorkMaterial(Course course) {
        return getCourseWorkMaterial.listAllWorkMaterial(course);
    }

    @Override
    public List<WorkMaterial> listAllWorkMaterial(Course course, String access) {
        return getCourseWorkMaterial.listAllWorkMaterial(course, access);
    }

    @Override
    public List<Announcement> getAllAnnouncementByCourse(Course course) {
        if (isInvalidCourse(course)) {
            return List.of();
        }

        return announcements.getAllAnnouncementByCourse(course);
    }

    private Boolean isInvalidCourse(Course course) {
        return course == null || course.getId() == null;
    }
}

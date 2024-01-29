package com.github.edulook.look.infra.repository.http;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.repository.CourseRepository;
import com.github.edulook.look.core.repository.course.GetCourse;
import com.github.edulook.look.core.repository.course.GetCourseAnnouncement;
import com.github.edulook.look.core.repository.course.GetCourseWork;
import com.github.edulook.look.core.repository.course.GetCourseWorkMaterial;
import com.google.api.services.classroom.Classroom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component("CourseRepositoryHTTP::Class")
public class CourseRepositoryHTTP implements CourseRepository {

    @Autowired
    private Classroom classroom;
    private final GetCourse getCourse;
    private final GetCourseWorkMaterial getCourseWorkMaterial;
    private final GetCourseWork getCourseWork;
    private final GetCourseAnnouncement getCourseAnnouncement;

    public CourseRepositoryHTTP(@Qualifier("GetCourseHTTP::Class") GetCourse getCourse,
                                @Qualifier("GetCourseWorkMaterialHTTP::Class") GetCourseWorkMaterial getCourseWorkMaterial,
                                @Qualifier("GetCourseWorkHTTP::Class") GetCourseWork getCourseWork,
                                @Qualifier("GetCourseAnnouncementHTTP::Class") GetCourseAnnouncement getCourseAnnouncement) {

        this.getCourse = getCourse;
        this.getCourseWorkMaterial = getCourseWorkMaterial;
        this.getCourseWork = getCourseWork;
        this.getCourseAnnouncement = getCourseAnnouncement;
    }

    @Override
    public List<Course> findCoursesByStudentId(String studentId) {
        return  getCourse.findCoursesByStudentId(studentId);
    }

    @Override
    public Optional<Course> findOneCourseByStudentId(String courseId, String studentId) {
        return getCourse.findOneCourseByStudentId(courseId, studentId);
    }

    @Override
    public List<Course> findCoursesByTeacherID(String teacherId) {
        return getCourse.findCoursesByTeacherID(teacherId);
    }

    @Override
    public Optional<Course> findOneCourseByTeacherID(String courseId, String teacherId) {
        return getCourse.findOneCourseByTeacherID(courseId, teacherId);
    }

    @Override
    public List<Course.WorkMaterial> listAllWorkMaterial(Course course) {
        return  getCourseWorkMaterial.listAllWorkMaterial(course);
    }

    @Override
    public Optional<Course.WorkMaterial> findOneMaterial(Course course, String materialId) {
        return getCourseWorkMaterial.findOneMaterial(course, materialId);
    }

    @Override
    public List<Course.WorkMaterial> listAllWorkMaterial(Course course, String access) {
        return getCourseWorkMaterial.listAllWorkMaterial(course, access);
    }

    @Override
    public List<Course.Announcement> getAllAnnouncementByCourse(Course course) {
        return getCourseAnnouncement.getAllAnnouncementByCourse(course);
    }

    @Override
    @CacheEvict(value = "findOneCourseMaterial", allEntries = true)
    public Course.WorkMaterial upsetCourseMaterial(Course.WorkMaterial materialSaved) {
        // TODO: add persistence here

        return materialSaved;
    }

    @Override
    public List<Course.WorkMaterial> listAllWorks(Course course) {
        return getCourseWork.listAllWorks(course);
    }

}

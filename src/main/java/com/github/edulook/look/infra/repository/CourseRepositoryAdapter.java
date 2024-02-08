package com.github.edulook.look.infra.repository;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.model.Announcement;
import com.github.edulook.look.core.model.WorkMaterial;
import com.github.edulook.look.core.repository.CourseRepository;
import com.github.edulook.look.infra.worker.events.course.WorkMaterialEvent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component("CourseRepositoryAdapter::Class")
public class CourseRepositoryAdapter implements CourseRepository {
    private final CourseRepository db;
    private final CourseRepository http;
    private final ApplicationEventPublisher publisher;


    public CourseRepositoryAdapter(@Qualifier("CourseRepositoryDB::Class") CourseRepository db,
                                   @Qualifier("CourseRepositoryHTTP::Class") CourseRepository http,
                                   ApplicationEventPublisher publisher) {
        this.db = db;
        this.http = http;
        this.publisher = publisher;
    }


    @Override
    public List<Course> findCoursesByStudentId(String studentId) {
        var courses = db.findCoursesByStudentId(studentId);
        if(courses.isEmpty())
            return http.findCoursesByStudentId(studentId);
        return courses;
    }

    @Override
    public Optional<Course> findOneCourseByStudentId(String courseId, String studentId) {
        var course = db.findOneCourseByStudentId(courseId, studentId);
        if(course.isEmpty())
            return http.findOneCourseByStudentId(courseId, studentId);
        return course;
    }

    @Override
    public List<Course> findCoursesByTeacherId(String teacherId) {
        var courses = db.findCoursesByTeacherId(teacherId);
        if(courses.isEmpty())
            return http.findCoursesByTeacherId(teacherId);
        return courses;
    }

    @Override
    public Optional<Course> findOneCourseByTeacherId(String courseId, String teacherId) {
        var course = db.findOneCourseByTeacherId(courseId, teacherId);
        if(course.isEmpty())
            return http.findOneCourseByTeacherId(courseId, teacherId);
        return course;
    }

    @Override
    public List<WorkMaterial> listAllWorkMaterial(Course course) {
        var materials = db.listAllWorkMaterial(course);
        if(materials.isEmpty())
            return http.listAllWorkMaterial(course);
        return materials;
    }

    @Override
    public Optional<WorkMaterial> findOneMaterial(Course course, String materialId) {
        var workMaterial = db.findOneMaterial(course, materialId);
        if(workMaterial.isEmpty())
            return http.findOneMaterial(course, materialId);
        return workMaterial;
    }

    @Override
    public List<WorkMaterial> listAllWorkMaterial(Course course, String access) {
        var materials = db.listAllWorkMaterial(course, access);
        if(materials.isEmpty())
            return http.listAllWorkMaterial(course, access);
        return materials;
    }

    @Override
    public List<Announcement> getAllAnnouncementByCourse(Course course) {
        if (Objects.isNull(course) || course.isNotValid())
            return List.of();
        var announcements = db.getAllAnnouncementByCourse(course);
        if(announcements.isEmpty())
            return http.getAllAnnouncementByCourse(course);
        return announcements;
    }


    @Override
    @CacheEvict(value = "findOneCourseMaterial", allEntries = true)
    public WorkMaterial upsetCourseMaterial(WorkMaterial materialSaved) {
        return db.upsetCourseMaterial(materialSaved);
    }

    @Override
    public List<WorkMaterial> listAllWorks(Course course) {
        var works = db.listAllWorks(course);
        if(works.isEmpty()) {
            works = http.listAllWorks(course);
            works.parallelStream()
                .map(WorkMaterialEvent::fromModel)
                .forEach(publisher::publishEvent);
        }
        return works;
    }
}
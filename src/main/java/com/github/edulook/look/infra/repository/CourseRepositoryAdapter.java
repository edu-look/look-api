package com.github.edulook.look.infra.repository;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.model.Course.Announcement;
import com.github.edulook.look.core.model.Course.WorkMaterial;
import com.github.edulook.look.core.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Primary
public class CourseRepositoryAdapter implements CourseRepository {

    private final CourseRepository storage;
    private final CourseRepository classroom;

    public CourseRepositoryAdapter(@Qualifier("CourseStorageRepository::Class")CourseRepository storage,
                                   @Qualifier("CourseClassroomRepository::Class")CourseRepository classroom) {
        this.storage = storage;
        this.classroom = classroom;
    }

    @Override
    public Optional<Course> save(Course model) {
        return storage.save(model);
    }

    @Override
    public Optional<Course> delete(String id) {
        return storage.delete(id);
    }

    @Override
    public List<Course> findCoursesByStudentId(String studentId) {
        var courses = storage.findCoursesByStudentId(studentId);
        if (courses.isEmpty())
            return classroom.findCoursesByStudentId(studentId);
        return courses;
    }

    @Override
    public Optional<Course> findOneCourseByStudentId(String courseId, String studentId) {
        var course = storage.findOneCourseByStudentId(courseId, studentId);
        if (course.isEmpty())
            return classroom.findOneCourseByStudentId(courseId, studentId);
        return course;
    }

    @Override
    public List<Announcement> getAllAnnouncementByCourse(Course course) {
        var courses = storage.getAllAnnouncementByCourse(course);
        if (courses.isEmpty())
            return classroom.getAllAnnouncementByCourse(course);
        return courses;
    }

    @Override
    public List<WorkMaterial> listAllWorks(Course course) {
        var works = storage.listAllWorks(course);
        if (works.isEmpty()){
            works = classroom.listAllWorks(course);
            storage.save(course);
        }
        return works;
    }

    @Override
    public List<WorkMaterial> listAllWorkMaterial(Course course, String access) {
        var works = storage.listAllWorkMaterial(course, access);
        if (works.isEmpty())
            return classroom.listAllWorkMaterial(course, access);
        return works;
    }

    @Override
    public List<WorkMaterial> listAllWorkMaterial(Course course) {
        var works = storage.listAllWorkMaterial(course);
        if (works.isEmpty())
            return classroom.listAllWorkMaterial(course);
        return works;
    }

    @Override
    public Optional<WorkMaterial> findOneMaterial(Course course, String materialId) {
        var material = storage.findOneMaterial(course, materialId);
        if (material.isEmpty())
            return classroom.findOneMaterial(course, materialId);
        return material;
    }

    @Override
    public WorkMaterial upsetCourseMaterial(WorkMaterial materialSaved) {
        return storage.upsetCourseMaterial(materialSaved);
    }
}
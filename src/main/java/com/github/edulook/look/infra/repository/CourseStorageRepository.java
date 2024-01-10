package com.github.edulook.look.infra.repository;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.repository.CourseRepository;
import com.github.edulook.look.core.repository.course.GetCourse;
import com.github.edulook.look.core.repository.course.GetCourseAnnouncement;
import com.github.edulook.look.core.repository.course.GetCourseWork;
import com.github.edulook.look.core.repository.course.GetCourseWorkMaterial;
import com.github.edulook.look.infra.repository.firestore.CourseFirestoreRepository;
import com.github.edulook.look.infra.repository.firestore.CourseStudentFirestoreRepository;
import com.github.edulook.look.infra.repository.firestore.WorkMaterialFirestoreRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("CourseStorageRepository::Class")
public class CourseStorageRepository implements CourseRepository {

    private final CourseStudentFirestoreRepository courseStudentFirestoreRepository;

    private final CourseFirestoreRepository courseFirestoreRepository;
    private final WorkMaterialFirestoreRepository workMaterialFirestoreRepository;
    private final GetCourse getCourseStorage;
    private final GetCourseWorkMaterial getCourseWorkMaterialStorage;
    private final GetCourseWork getCourseWorkStorage;
    private final GetCourseAnnouncement getCourseAnnouncementStorage;

    public CourseStorageRepository(CourseStudentFirestoreRepository courseStudentFirestoreRepository, CourseFirestoreRepository courseFirestoreRepository, WorkMaterialFirestoreRepository workMaterialFirestoreRepository,
                                   @Qualifier("GetCourseStorage::Class") GetCourse getCourseStorage,
                                   @Qualifier("GetCourseWorkStorage:Class") GetCourseWork getCourseWorkStorage,
                                   @Qualifier("GetCourseWorkMaterialStorage::Class") GetCourseWorkMaterial getCourseWorkMaterialStorage,
                                   @Qualifier("GetCourseAnnouncementStorage::Class") GetCourseAnnouncement getCourseAnnouncementStorage) {
        this.courseStudentFirestoreRepository = courseStudentFirestoreRepository;
        this.courseFirestoreRepository = courseFirestoreRepository;
        this.workMaterialFirestoreRepository = workMaterialFirestoreRepository;
        this.getCourseStorage = getCourseStorage;
        this.getCourseWorkStorage = getCourseWorkStorage;
        this.getCourseWorkMaterialStorage = getCourseWorkMaterialStorage;
        this.getCourseAnnouncementStorage = getCourseAnnouncementStorage;
    }

    @Override
    public Optional<Course> save(Course model) {
        return courseFirestoreRepository.save(model).blockOptional();
    }

    @Override
    public Optional<Course> delete(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Course> findCoursesByStudentId(String studentId) {
        return getCourseStorage.findCoursesByStudentId(studentId);
    }

    @Override
    public Optional<Course> findOneCourseByStudentId(String courseId, String studentId) {
        return getCourseStorage.findOneCourseByStudentId(courseId, studentId);
    }

    @Override
    public List<Course.Announcement> getAllAnnouncementByCourse(Course course) {
        return getCourseAnnouncementStorage.getAllAnnouncementByCourse(course);
    }

    @Override
    public List<Course.WorkMaterial> listAllWorks(Course course) {
        return getCourseWorkStorage.listAllWorks(course);
    }

    @Override
    public List<Course.WorkMaterial> listAllWorkMaterial(Course course, String access) {
        return getCourseWorkMaterialStorage.listAllWorkMaterial(course);
    }

    @Override
    public List<Course.WorkMaterial> listAllWorkMaterial(Course course) {
        return getCourseWorkMaterialStorage.listAllWorkMaterial(course);
    }

    @Override
    public Optional<Course.WorkMaterial> findOneMaterial(Course course, String materialId) {
        return getCourseWorkMaterialStorage.findOneMaterial(course, materialId);
    }

    @Override
    public Course.WorkMaterial upsetCourseMaterial(Course.WorkMaterial materialSaved) {
        throw new UnsupportedOperationException();
    }
}

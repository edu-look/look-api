package com.github.edulook.look.infra.repository;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.model.Course.Announcement;
import com.github.edulook.look.core.model.Course.WorkMaterial;
import com.github.edulook.look.core.repository.CourseRepository;
import com.github.edulook.look.core.repository.course.GetCourse;
import com.github.edulook.look.core.repository.course.GetCourseAnnouncement;
import com.github.edulook.look.core.repository.course.GetCourseWork;
import com.github.edulook.look.core.repository.course.GetCourseWorkMaterial;
import com.github.edulook.look.infra.worker.events.course.WorkMaterialEvent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CourseRepositoryAdapter implements CourseRepository {

    private final GetCourse getCourseClassroom;
    private final GetCourse getCourseStorage;
    private final GetCourseWorkMaterial getCourseWorkMaterialClassroom;
    private final GetCourseWorkMaterial getCourseWorkMaterialStorage;
    private final GetCourseWork getCourseWorkClassroom;
    private final GetCourseWork getCourseWorkStorage;
    private final GetCourseAnnouncement getCourseAnnouncementClassroom;
    private final GetCourseAnnouncement getCourseAnnouncementStorage;
    private final ApplicationEventPublisher publisher;

    public CourseRepositoryAdapter(@Qualifier("GetCourseClassroom::Class") GetCourse getCourseClassroom,
                                   @Qualifier("GetCourseStorage::Class") GetCourse getCourseStorage,
                                   @Qualifier("GetCourseWorkMaterialClassroom::Class") GetCourseWorkMaterial getCourseWorkMaterialClassroom,
                                   @Qualifier("GetCourseWorkClassroom::Class") GetCourseWork getCourseWorkClassroom,
                                   @Qualifier("GetCourseWorkStorage:Class") GetCourseWork getCourseWorkStorage,
                                   @Qualifier("GetCourseWorkMaterialStorage::Class") GetCourseWorkMaterial getCourseWorkMaterialStorage,
                                   @Qualifier("GetCourseAnnouncementClassroom::Class") GetCourseAnnouncement getCourseAnnouncementClassroom,
                                   @Qualifier("GetCourseAnnouncementStorage::Class") GetCourseAnnouncement getCourseAnnouncementStorage, ApplicationEventPublisher publisher) {
        this.getCourseClassroom = getCourseClassroom;
        this.getCourseStorage = getCourseStorage;
        this.getCourseWorkMaterialClassroom = getCourseWorkMaterialClassroom;
        this.getCourseWorkClassroom = getCourseWorkClassroom;
        this.getCourseWorkStorage = getCourseWorkStorage;
        this.getCourseWorkMaterialStorage = getCourseWorkMaterialStorage;
        this.getCourseAnnouncementClassroom = getCourseAnnouncementClassroom;
        this.getCourseAnnouncementStorage = getCourseAnnouncementStorage;
        this.publisher = publisher;
    }


    @Override
    public List<Course> findCoursesByStudentId(String studentId) {
        var courses = getCourseStorage.findCoursesByStudentId(studentId);

        if(courses.isEmpty())
            return getCourseClassroom.findCoursesByStudentId(studentId);

        return courses;
    }

    @Override
    public Optional<Course> findOneCourseByStudentId(String courseId, String studentId) {
        var course = getCourseStorage.findOneCourseByStudentId(courseId, studentId);

        if(course.isEmpty())
            return getCourseClassroom.findOneCourseByStudentId(courseId, studentId);

        return course;
    }

    @Override
    public List<WorkMaterial> listAllWorkMaterial(Course course) {
        var materials = getCourseWorkMaterialStorage.listAllWorkMaterial(course);

        if(materials.isEmpty())
            return getCourseWorkMaterialClassroom.listAllWorkMaterial(course);

        return materials;
    }

    @Override
    public Optional<WorkMaterial> findOneMaterial(Course course, String materialId) {
        var workMaterial = getCourseWorkMaterialStorage.findOneMaterial(course, materialId);

        if(workMaterial.isEmpty())
            return getCourseWorkMaterialClassroom.findOneMaterial(course, materialId);

        return workMaterial;
    }

    @Override
    public List<WorkMaterial> listAllWorkMaterial(Course course, String access) {
        var materials = getCourseWorkMaterialStorage.listAllWorkMaterial(course, access);

        if(materials.isEmpty())
            return getCourseWorkMaterialClassroom.listAllWorkMaterial(course, access);

        return materials;
    }

    @Override
    public List<Announcement> getAllAnnouncementByCourse(Course course) {
        if (isInvalidCourse(course)) {
            return List.of();
        }

        var announcements = getCourseAnnouncementStorage.getAllAnnouncementByCourse(course);

        if(announcements.isEmpty())
            return getCourseAnnouncementClassroom.getAllAnnouncementByCourse(course);

        return announcements;
    }

    private Boolean isInvalidCourse(Course course) {
        return course == null || course.getId() == null;
    }

    @Override
    @CacheEvict(value = "findOneCourseMaterial", allEntries = true)
    public WorkMaterial upsetCourseMaterial(WorkMaterial materialSaved) {
        // TODO: add persistence here

        return materialSaved;
    }

    @Override
    public List<WorkMaterial> listAllWorks(Course course) {
        var works = getCourseWorkStorage.listAllWorks(course);
        if(works.isEmpty()) {
            works = getCourseWorkClassroom.listAllWorks(course);

            works.parallelStream()
                .map(WorkMaterialEvent::fromModel)
                .forEach(publisher::publishEvent);
        }

        return works;
    }
}

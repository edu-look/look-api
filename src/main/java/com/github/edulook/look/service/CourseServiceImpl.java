package com.github.edulook.look.service;

import java.io.IOException;
import java.util.List;

import com.github.edulook.look.core.service.CourseService;
import com.github.edulook.look.infra.worker.events.course.AnnouncementEvent;
import com.github.edulook.look.infra.worker.events.course.WorkMaterialEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.model.Course.Announcement;
import com.github.edulook.look.core.model.Course.WorkMaterial;
import com.github.edulook.look.core.repository.CourseRepository;

import lombok.AllArgsConstructor;

/**
 * Facade courses service 
 */
@Slf4j
@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ApplicationEventPublisher publisher;

    @Cacheable("listCourses")
    public List<Course> listCourses(String studentId) throws IOException {
       return courseRepository.findCoursesByStudentId(studentId);
    }

    @Cacheable("listAllWorkMaterials")
    public List<WorkMaterial> listAllWorkMaterials(String courseId, String access) {
        if(courseId == null) {
            return List.of();
        }

        var course = Course.builder()
            .id(courseId)
            .build();

        var workMaterials = courseRepository.listAllWorkMaterial(course, access);

        workMaterials.forEach(it -> publisher.publishEvent(WorkMaterialEvent.fromModel(it)));

        return workMaterials;
    }


    @Cacheable("listAllAnnouncements")
    public List<Announcement> listAllAnnouncements(String courseId, String studentId) {
        var course = courseRepository
            .findOneCourseByStudentId(courseId, studentId);

        if(course.isEmpty()) {
            return List.of();
        }

        var announcements = courseRepository
                .getAllAnnouncementByCourse(course.get());

        announcements.forEach(it -> publisher.publishEvent(AnnouncementEvent.fromModel(it)));

        return announcements;
    }
}

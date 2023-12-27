package com.github.edulook.look.infra.worker;


import com.github.edulook.look.core.repository.CourseRepository;
import com.github.edulook.look.infra.worker.events.course.AnnouncementEvent;
import com.github.edulook.look.infra.worker.events.course.CourseEvent;
import com.github.edulook.look.infra.worker.events.course.WorkMaterialEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;


@Slf4j
@Configuration
public class CourseWorker {

    private final CourseRepository courseRepository;

    public CourseWorker(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @EventListener
    public void processorWorkMaterial(WorkMaterialEvent course) {
    }

    @EventListener
    public void processorAnnouncement(AnnouncementEvent announcement) {
        log.info("saving announcement {}", announcement.getCourseId());
    }

    @EventListener
    public void eventCourse(CourseEvent courseEvent) {
        courseRepository.save(courseEvent.getCourse());

    }
}

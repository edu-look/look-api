package com.github.edulook.look.infra.worker;


import com.github.edulook.look.infra.worker.events.course.AnnouncementEvent;
import com.github.edulook.look.infra.worker.events.course.WorkMaterialEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CourseWorker {

    @EventListener
    public void processorWorkMaterial(WorkMaterialEvent course) {
      log.info("saving work material {}", course.getCourseId());
    }

    @EventListener
    public void processorAnnouncement(AnnouncementEvent announcement) {
        log.info("saving announcement {}", announcement.getCourseId());
    }
}
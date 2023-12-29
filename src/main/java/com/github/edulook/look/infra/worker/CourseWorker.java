package com.github.edulook.look.infra.worker;


import com.github.edulook.look.core.repository.CourseRepository;
import com.github.edulook.look.infra.worker.events.course.AnnouncementEvent;
import com.github.edulook.look.infra.worker.events.course.CheckMaterialLinkEditEvent;
import com.github.edulook.look.infra.worker.events.course.CourseEvent;
import com.github.edulook.look.infra.worker.events.course.WorkMaterialEvent;
import com.google.api.services.classroom.Classroom;
import com.google.api.services.classroom.model.CourseWorkMaterial;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;
import java.util.regex.Pattern;

@EnableAsync
@Slf4j
@Configuration
public class CourseWorker {
    @Value("${server.address:localhost}")
    private String address;

    private final Classroom classroom;
    private final CourseRepository courseRepository;

    public CourseWorker(Classroom classroom, CourseRepository courseRepository) {
        this.classroom = classroom;
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

    @Async
    @EventListener
    public void processorMaterialLinkEdit(CheckMaterialLinkEditEvent event) {
        var material = event.getMaterial();
        Pattern urlPattern = Pattern.compile(
                "(http://www\\\\.|https://www\\\\.|http://|https://)?[a-z0-9]+([\\\\-\\\\.]{1}[a-z0-9]+)*\\\\.[a-z]{2,5}(:[0-9]{1,5})?(/v1/courses/[a-z0-9]+/materials/[a-z0-9]+/edit)?",
                Pattern.CASE_INSENSITIVE
        );
        var matcher = urlPattern.matcher(material.getDescription());
        if (matcher.find()) return;

        var endpointEdit = String.format("%s/v1/courses/%s/materials/%s/edit", address, event.getCourseId(), event.getMaterialId());

        var updatedMaterial = new CourseWorkMaterial();
        updatedMaterial.setDescription(endpointEdit);

        try {
            classroom.courses().courseWorkMaterials().patch(event.getCourseId(), event.getMaterialId(), updatedMaterial)
                    .setUpdateMask("description")
                    .execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

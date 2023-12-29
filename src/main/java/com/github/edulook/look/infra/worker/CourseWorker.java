package com.github.edulook.look.infra.worker;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.repository.CourseRepository;
import com.github.edulook.look.endpoint.io.course.CourseDTO;
import com.github.edulook.look.infra.worker.events.course.AnnouncementEvent;
import com.github.edulook.look.infra.worker.events.course.CheckMaterialLinkEditEvent;
import com.github.edulook.look.infra.worker.events.course.WorkMaterialEvent;
import com.google.api.services.classroom.Classroom;
import com.google.api.services.classroom.model.CourseWorkMaterial;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@EnableAsync
@Slf4j
@Configuration
public class CourseWorker {
    @Value("${server.address}")
    private String address;
    private final FirebaseApp firebaseApp;
    private final Classroom classroom;


    public CourseWorker(FirebaseApp firebaseApp, Classroom classroom) {
        this.firebaseApp = firebaseApp;
        this.classroom = classroom;
    }

    @EventListener
    public void processorWorkMaterial(WorkMaterialEvent course) {
        log.info("saving work materialmaterial {}", course.getCourseId());
        DatabaseReference workMaterialRef = FirebaseDatabase.getInstance(firebaseApp).getReference("workMaterials");
        String workMaterialKey = workMaterialRef.push().getKey();
        Course courseMaterial = new Course(
                course.getCourseId(),
                course.getId(),
                course.getDescription(),
                course.getTitle(),
                course.getCreatedAt()
        );

        // Salvando os dados no banco de dados
        workMaterialRef.child(workMaterialKey).setValue(courseMaterial, (databaseError, databaseReference) -> {
            if (databaseError != null) {
                log.error("Data could not be saved. " + databaseError.getMessage());
            } else {
                log.info("Work material saved with key: {}", workMaterialKey);
            }
        });
    }

    @EventListener
    public void processorAnnouncement(AnnouncementEvent announcement) {
        log.info("saving announcement {}", announcement.getCourseId());
    }

    @Async
    @EventListener
    public void processorMaterialLinkEdit(CheckMaterialLinkEditEvent event) {
        var material = event.getMaterial();
        Pattern urlPattern = Pattern.compile(
                "(http://www\\\\.|https://www\\\\.|http://|https://)?[a-z0-9]+([\\\\-\\\\.]{1}[a-z0-9]+)*\\\\.[a-z]{2,5}(:[0-9]{1,5})?(/v1/courses/[a-z0-9]+/materials/[a-z0-9]+/edit)?",
                Pattern.CASE_INSENSITIVE
        );
        Matcher matcher = urlPattern.matcher(material.getDescription());
        if (matcher.find()) return;

        var endpointEdit = String.format("%s/v1/courses/%s/materials/%s/edit", address, event.getCourseId(), event.getMaterialId());

        CourseWorkMaterial updatedMaterial = new CourseWorkMaterial();
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

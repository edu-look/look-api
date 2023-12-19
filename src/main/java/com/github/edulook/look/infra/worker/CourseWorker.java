package com.github.edulook.look.infra.worker;


import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.repository.CourseRepository;
import com.github.edulook.look.endpoint.io.course.CourseDTO;
import com.github.edulook.look.infra.worker.events.course.AnnouncementEvent;
import com.github.edulook.look.infra.worker.events.course.WorkMaterialEvent;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Configuration
public class CourseWorker {

    private final FirebaseApp firebaseApp;

    public CourseWorker (FirebaseApp firebaseApp){
        this.firebaseApp = firebaseApp;
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
}

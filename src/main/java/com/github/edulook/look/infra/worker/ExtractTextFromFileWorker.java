package com.github.edulook.look.infra.worker;

import com.github.edulook.look.core.exceptions.ResourceNotFoundException;
import com.github.edulook.look.core.exceptions.TextExtractInvalidException;
import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.repository.CourseRepository;
import com.github.edulook.look.infra.worker.events.course.CourseMaterialExtractPDFEvent;
import com.github.edulook.look.service.DriveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.File;
import java.util.Optional;

@Slf4j
@Configuration
@EnableAsync
public class ExtractTextFromFileWorker {
    private final DriveService driveService;
    private final CourseRepository courseRepository;

    public ExtractTextFromFileWorker(DriveService driveService, CourseRepository courseRepository) {
        this.driveService = driveService;
        this.courseRepository = courseRepository;
    }


    @Async
    @EventListener
    public void workMaterialProcessor(CourseMaterialExtractPDFEvent event) {
        var pdfFile = downloadFile(event)
                .orElseThrow(TextExtractInvalidException::new);


    }

    private Optional<File> downloadFile(CourseMaterialExtractPDFEvent event) {
        var course = Course.builder()
            .id(event.getCourseId())
            .build();

        var materialSaved = courseRepository
                .findOneMaterial(course, event.getMaterialId());

        if(materialSaved.isEmpty()) {
            log.warn("can't found material '{}' to course '{}'", event.getMaterialId(), course.getId());
            return Optional.empty();
        }

        var material = materialSaved.get();

        var content = material.getMaterials().stream()
            .filter(it -> it.getId().equalsIgnoreCase(event.getContentId()))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException(String.format("content '%s' not found to course '%s' and material  '%s'", event.getContentId(), event.getCourseId(), event.getMaterialId())));

        return driveService
            .downloadViaSharedLink(content.getOriginLink());
    }
}

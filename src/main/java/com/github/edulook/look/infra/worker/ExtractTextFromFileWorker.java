package com.github.edulook.look.infra.worker;

import com.github.edulook.look.core.exceptions.TextExtractInvalidException;
import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.repository.CourseRepository;
import com.github.edulook.look.infra.worker.events.course.CourseMaterialExtractPDFEvent;
import com.google.api.services.classroom.Classroom;
import com.google.api.services.drive.Drive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.security.Principal;
import java.util.regex.Pattern;

import static com.github.edulook.look.utils.LookUtils.toUserDTO;

@Slf4j
@Configuration
@EnableAsync
public class ExtractTextFromFileWorker {
    private final Drive drive;
    private final CourseRepository courseRepository;

    public ExtractTextFromFileWorker(Drive drive, CourseRepository courseRepository) {
        this.drive = drive;
        this.courseRepository = courseRepository;
    }

    @Async
    @EventListener
    public void workMaterialProcessor(CourseMaterialExtractPDFEvent event) {
        var pdfFile = downloadFileFromGDrive(event);
    }

    private File downloadFileFromGDrive(CourseMaterialExtractPDFEvent event) {
        var course = Course.builder()
            .id(event.getCourseId())
            .build();

        var materialSaved = courseRepository
                .findOneMaterial(course, event.getMaterialId());

        if(materialSaved.isEmpty()) {
            var message = String.format("can't found material '%s' to course '%s'",
                    event.getMaterialId(),
                    course.getId());
            log.warn(message);
            throw new TextExtractInvalidException(message);
        }

        var material = materialSaved.get();

        var content = material.getMaterials().stream()
                .filter(it -> it.getId().equalsIgnoreCase(event.getContentId()))
                .findFirst()
                .get();

        var fileId = getIdFromDriveFile(content.getOriginLink());

        if(fileId.isBlank()) {
            var message = "file id is invalid";
            log.warn(message);
            throw new TextExtractInvalidException(message);
        }

        try {
            var originFile = drive.files()
                .get(fileId)
                .execute();

            var outputStream = new ByteArrayOutputStream();
            var saveTo = new File(originFile.getName());

            drive.files()
                .get(originFile.getId())
                .executeAndDownloadTo(outputStream);

            try(var fileOutputStream = new FileOutputStream(saveTo)) {
               fileOutputStream.write(outputStream.toByteArray());
            }

            return saveTo;

        } catch (IOException e) {
            throw new TextExtractInvalidException(e);
        }
    }

    private String getIdFromDriveFile(String url) {
        var regex = "\\/d\\/(?<id>[\\w+_-]+)\\/";

        var pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        var matcher = pattern.matcher(url);

        if(matcher.find())
            return matcher.group("id");

        return "";
    }
}

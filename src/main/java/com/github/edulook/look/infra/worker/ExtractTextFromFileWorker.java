package com.github.edulook.look.infra.worker;

import com.github.edulook.look.core.data.PageContent;
import com.github.edulook.look.core.data.Range;
import com.github.edulook.look.core.exceptions.ResourceNotFoundException;
import com.github.edulook.look.core.exceptions.TextExtractInvalidException;
import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.repository.CourseRepository;
import com.github.edulook.look.infra.worker.events.course.CourseMaterialExtractPDFEvent;
import com.github.edulook.look.service.DriveService;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessReadBufferedFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
        var range = event.getRange();
        if(range.isNotValid()) {
            log.warn("Range invalid {}", range);
            return;
        }

        var material = getWorkMaterial(event.getCourseId(), event.getMaterialId())
            .orElseThrow(ResourceNotFoundException::new);

        var pdfFile = downloadFile(material, event.getContentId())
            .orElseThrow(TextExtractInvalidException::new);

        try (var document = Loader.loadPDF(new RandomAccessReadBufferedFile(pdfFile))) {
            var stripper = new PDFTextStripper();

            var contentPDF = material.getMaterials().stream()
                .filter(it -> it.getId().equalsIgnoreCase(event.getContentId()))
                .findFirst()
                .orElseThrow(ResourceNotFoundException::new);

            var pages = new ArrayList<PageContent.Page>();
            for (var currentPage = range.getStart(); currentPage <= range.getEnd(); currentPage++) {
                stripper.setStartPage(range.getStart());
                stripper.setEndPage(range.getEnd());
                var page = PageContent.Page.builder()
                    .page(currentPage)
                    .content(stripper.getText(document))
                    .build();
                pages.add(page);
            }

            contentPDF.setContent(Optional.ofNullable(PageContent.builder()
                .pages(pages)
                .size(pages.size())
                .build()));

            material.setMaterials(material.getMaterials().stream()
                .map(it -> contentPDF.getId().equalsIgnoreCase(it.getId()) ? contentPDF : it)
                .toList());

            courseRepository.upsetCourseMaterial(material);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<File> downloadFile(Course.WorkMaterial material, String contentId) {
        var content = material.getMaterials().stream()
            .filter(it -> it.getId().equalsIgnoreCase(contentId))
            .findFirst();

        return content.flatMap(it -> driveService.downloadViaSharedLink(it.getOriginLink()));
    }

    private Optional<Course.WorkMaterial> getWorkMaterial(String courseId, String materialId) {
        var course = Course.builder()
            .id(courseId)
            .build();

        return courseRepository.findOneMaterial(course, materialId);
    }
}

package com.github.edulook.look.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.github.edulook.look.core.data.Typename;
import com.github.edulook.look.core.exceptions.ResourceNotFoundException;
import com.github.edulook.look.endpoint.io.course.MaterialDTO;
import com.github.edulook.look.endpoint.io.shared.UserAuthDTO;
import com.github.edulook.look.infra.worker.events.course.AnnouncementEvent;
import com.github.edulook.look.infra.worker.events.course.CourseMaterialExtractPDFEvent;
import com.github.edulook.look.infra.worker.events.course.WorkMaterialEvent;
import com.github.edulook.look.service.usecase.extrator.pdf.PDFClassificationEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.model.Course.Announcement;
import com.github.edulook.look.core.model.Course.WorkMaterial;
import com.github.edulook.look.core.model.Course.WorkMaterial.Material;
import com.github.edulook.look.core.repository.CourseRepository;

import lombok.AllArgsConstructor;

/**
 * Facade courses service 
 */
@Slf4j
@Service
@AllArgsConstructor
public class CourseService {

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

    public WorkMaterial upsetCourseMaterial(String courseId, String materialId, MaterialDTO material) {
        var materialSavedOptional = courseRepository.findOneMaterial(Course.builder().id(courseId).build(), materialId);

        if(materialSavedOptional.isEmpty())
            throw new ResourceNotFoundException(String.format("material '%s' not found", materialId));

        var materialSaved = materialSavedOptional.get();
        materialSaved.setDescription(material.description());

        materialSaved.getMaterials().parallelStream().forEach(it -> {
            try {
                var materialSelectedDTO = material.materials().stream()
                    .filter(mat -> mat.id().equalsIgnoreCase(it.getId()))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException(String.format("material %s not found", it.getId())));

                it.setName(materialSelectedDTO.name());
                it.setOption(materialSelectedDTO.option());
                it.setDescription(materialSelectedDTO.description());

                emitContentParserEvent(it, materialSaved);
            }
            catch (Exception e) {
                log.error("error:: ", e);
            }
        });

        return courseRepository.upsetCourseMaterial(materialSaved);
    }

    private void emitContentParserEvent(Material it, WorkMaterial materialSaved) {
        if(!it.getType().equalsIgnoreCase(Typename.PDF))
            return;

        it.getOption().ifPresent(option -> {
            var classification = option.isEnableOCR()
                ? PDFClassificationEnum.NOREGULAR
                : PDFClassificationEnum.REGULAR;

            var event = CourseMaterialExtractPDFEvent.builder()
                .contentId(it.getId())
                .courseId(materialSaved.getCourseId())
                .materialId(materialSaved.getId())
                .option(it.getOption().get())
                .classification(classification)
                .build();

            publisher.publishEvent(event);
        });
    }

    @Cacheable("findOneCourseMaterial")
    public Optional<WorkMaterial> findOneCourseMaterial(String courseId, String materialId) {
        var course = Course.builder()
            .id(courseId)
            .build();

        return courseRepository
            .findOneMaterial(course, materialId);
    }

    public List<WorkMaterial> findAllCourseWorks(String courseId) {
        var course = Course.builder()
            .id(courseId)
            .build();

        return courseRepository.listAllWorks(course);
    }
}

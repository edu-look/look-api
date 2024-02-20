package com.github.edulook.look.service;

import com.github.edulook.look.core.exceptions.ResourceNotFoundException;
import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.model.Announcement;
import com.github.edulook.look.core.model.WorkMaterial;
import com.github.edulook.look.core.model.Material;
import com.github.edulook.look.core.repository.CourseRepository;
import com.github.edulook.look.endpoint.io.course.MaterialDTO;
import com.github.edulook.look.infra.worker.events.course.AnnouncementEvent;
import com.github.edulook.look.infra.worker.events.course.CourseMaterialExtractPDFEvent;
import com.github.edulook.look.infra.worker.events.course.WorkMaterialEvent;
import com.github.edulook.look.infra.tools.PDFClassificationEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final ApplicationEventPublisher publisher;

    public CourseService(CourseRepository courseRepository, ApplicationEventPublisher publisher) {
        this.courseRepository = courseRepository;
        this.publisher = publisher;
    }

    @Cacheable("listCourses")
    public List<Course> listCourses(String studentId) {
       return courseRepository.findCoursesByStudentId(studentId);
    }

    @Cacheable("listCourses")
    public List<Course> listCoursesTeacher(String teacherId) {
        return courseRepository.findCoursesByTeacherId(teacherId);
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

        var announcements = courseRepository.getAllAnnouncementByCourse(course.get());

        announcements.forEach(it -> publisher.publishEvent(AnnouncementEvent.fromModel(it)));

        return announcements;
    }

    public WorkMaterial upsetCourseMaterial(String courseId, String materialId, MaterialDTO materialDTO) {
        var course = Course.builder()
            .id(courseId)
            .build();

        var material = courseRepository
            .findOneMaterial(course, materialId)
            .orElseThrow(() -> new ResourceNotFoundException(String.format("material '%s' not found", materialId)));

        updateWorkMaterial(material, materialDTO);

        return courseRepository
            .upsetCourseMaterial(material)
            .also(it -> it.forEachMaterial(content -> emitContentParserEvent(content, it)));
    }

    private void updateWorkMaterial(WorkMaterial workMaterial, MaterialDTO materialDTO) {
        workMaterial.forEachMaterial(material -> replaceOldContentForDTOContent(material, materialDTO));
        workMaterial.setDescription(materialDTO.description());
    }

    private void replaceOldContentForDTOContent(Material material, MaterialDTO materialDTO) {
        try {
            var materialSelectedDTO = materialDTO.materials().stream()
                .filter(mat -> mat.id().equalsIgnoreCase(material.getId()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(String.format("material %s not found", material.getId())));

            material.setName(materialSelectedDTO.name());
            material.setOption(materialSelectedDTO.option().orElse(null));
            material.setDescription(materialSelectedDTO.description());
        }
        catch (Exception e) {
            log.error("error:: ", e);
        }
    }

    private void emitContentParserEvent(Material material, WorkMaterial workMaterial) {
        if(Objects.isNull(material) || material.notPDFContent())
            return;

        material.ifOptionPresent(it -> {
            var classification = it.isEnableOCR()
                ? PDFClassificationEnum.NOREGULAR
                : PDFClassificationEnum.REGULAR;

            var event = CourseMaterialExtractPDFEvent.builder()
                .contentId(material.getId())
                .courseId(workMaterial.getCourseId())
                .materialId(workMaterial.getId())
                .option(it)
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

package com.github.edulook.look.endpoint;

import com.github.edulook.look.core.model.Course.Announcement;
import com.github.edulook.look.core.model.Course.WorkMaterial;
import com.github.edulook.look.endpoint.internal.mapper.course.CourseAndDTOMapper;
import com.github.edulook.look.endpoint.io.course.CourseDTO;
import com.github.edulook.look.endpoint.io.course.MaterialDTO;
import com.github.edulook.look.endpoint.io.shared.UserAuthDTO;
import com.github.edulook.look.service.CourseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("v1/courses")
public class CourseEndpoint {

    private final CourseService courseService;
    private final CourseAndDTOMapper courseAndDTOMapper;

    public CourseEndpoint(CourseService courseService, CourseAndDTOMapper courseAndDTOMapper) {
        this.courseService = courseService;
        this.courseAndDTOMapper = courseAndDTOMapper;
    }

    @GetMapping
    public List<CourseDTO> getCourses(@RequestAttribute("user") UserAuthDTO user) throws IOException {
        log.info("user logged: {}", user.id());

        return courseAndDTOMapper
            .toDTOList(courseService.listCourses(user.id()));
    }

    @GetMapping("{courseId}/announcements")
    public List<Announcement> listAllAnnouncements(@PathVariable String courseId, @RequestAttribute("user") UserAuthDTO user) {
        log.info("user logged: {}", user.id());
        log.info("announcements to course: {}", courseId);

        return courseService
           .listAllAnnouncements(courseId, user.id());
    }

    @GetMapping("{courseId}/materials")
    public List<WorkMaterial> listAllWorkMaterials(@PathVariable String courseId, @RequestAttribute("user") UserAuthDTO user) {
        log.info("user logged: {}", user.id());
        log.info("materials to course: {}", courseId);

        return courseService.listAllWorkMaterials(courseId, user.jwt().token());
    }

    @GetMapping("{courseId}/materials/{materialId}")
    public Optional<MaterialDTO> findOneCourseMaterial(@PathVariable String courseId,
                                                       @PathVariable String materialId,
                                                       @RequestAttribute("user") UserAuthDTO user) {

        return courseService.findOneCourseMaterial(courseId, materialId)
            .map(courseAndDTOMapper::toDTO);
    }



    @PatchMapping("{courseId}/materials/{materialId}/edit")
    public WorkMaterial listAllWorkMaterialsUpdate(@PathVariable String courseId,
                                                         @PathVariable String materialId,
                                                         @RequestBody MaterialDTO material,
                                                         @RequestAttribute("user") UserAuthDTO user) {

        return courseService.upsetCourseMaterial(courseId, materialId, material);
    }
}

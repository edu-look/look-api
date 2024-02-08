package com.github.edulook.look.infra.repository.http.course;

import com.github.edulook.look.core.model.Course;
import com.github.edulook.look.core.model.WorkMaterial;
import com.github.edulook.look.core.repository.course.GetCourseWorkMaterial;
import com.github.edulook.look.infra.repository.http.course.mapper.ClassroomMaterialToCourseMaterialMapper;
import com.google.api.services.classroom.Classroom;
import com.google.api.services.classroom.model.CourseWorkMaterial;
import com.google.api.services.classroom.model.ListCourseWorkMaterialResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component("GetCourseWorkMaterialHTTP::Class")
public class GetCourseWorkMaterialHTTP implements GetCourseWorkMaterial {
    private final Classroom classroom;
    private final ClassroomMaterialToCourseMaterialMapper classroomMaterialToCourseMaterialMapper;

    public GetCourseWorkMaterialHTTP(Classroom classroom,
                                     ClassroomMaterialToCourseMaterialMapper classroomMaterialToCourseMaterialMapper) {
        this.classroom = classroom;
        this.classroomMaterialToCourseMaterialMapper = classroomMaterialToCourseMaterialMapper;
    }

    @Override
    public List<WorkMaterial> listAllWorkMaterial(Course course) {
        return listAllWorkMaterial(course, null);
    }

    @Override
    public Optional<WorkMaterial> findOneMaterial(Course course, String materialId) {
        try {
            var material = classroom.courses()
                .courseWorkMaterials()
                .get(course.getId(), materialId)
                .execute();
            return Optional.ofNullable(toWorkMaterialCore(material));
        } catch (IOException e) {
            log.error("error:: ", e);
        }
        return Optional.empty();
    }

    @Override
    public List<WorkMaterial> listAllWorkMaterial(Course course, String access) {
        try {
            var materials = getAllWorkMaterialFromClassroom(course, access);
            if(materials.isEmpty())
                return List.of();

            return toWorkMaterialCore(materials.get());
        } catch (IOException e) {
            log.error("error:: ", e);
        }
        return List.of();
    }

    private Optional<ListCourseWorkMaterialResponse> getAllWorkMaterialFromClassroom(Course course, String access) throws IOException {
        var client = classroom.courses()
            .courseWorkMaterials()
            .list(course.getId());
        client =  (access == null || access.isBlank())
            ? client
            : client.setAccessToken(access);
        var materials = client.execute();
        if(materials == null || materials.isEmpty())
            return Optional.empty();
        return Optional.of(materials);
    }

    private List<WorkMaterial> toWorkMaterialCore(ListCourseWorkMaterialResponse materials) {
        var workMaterial = Optional.ofNullable(materials.getCourseWorkMaterial());
        return workMaterial.map(courseWorkMaterials -> courseWorkMaterials
            .parallelStream()
            .map(this::toWorkMaterialCore)
            .toList())
            .orElseGet(List::of);
    }

    private WorkMaterial toWorkMaterialCore(CourseWorkMaterial material) {
        var materialsCore = material.getMaterials()
            .parallelStream()
            .map(classroomMaterialToCourseMaterialMapper::toModel)
            .filter(Objects::nonNull)
            .toList();
        return WorkMaterial
            .builder()
            .createdAt(material.getCreationTime())
            .title(material.getTitle())
            .id(material.getId())
            .courseId(material.getCourseId())
            .description(material.getDescription())
            .materials(materialsCore)
            .build();
    }
}

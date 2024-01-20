package com.github.edulook.look.infra.repository.http.course.mapper;

import com.github.edulook.look.core.model.Course.WorkMaterial;
import com.github.edulook.look.infra.repository.http.course.mapper.factories.FactoryCourseMaterial;
import com.google.api.services.classroom.model.CourseWork;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

import static com.github.edulook.look.core.model.Course.WorkMaterial.Material;

@Component
@NoArgsConstructor
public class ClassroomMaterialToCourseMaterialMapper {
    public Material toModel(com.google.api.services.classroom.model.Material source) {
        var factory = FactoryCourseMaterial.create(source);
        return factory.create(source);
    }

    public List<WorkMaterial> toModel(List<CourseWork> source) {
        return source
            .stream()
            .map(material -> {
                var materialsCore = material
                    .getMaterials()
                    .stream()
                    .map(this::toModel)
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
            })
            .toList();
    }
}
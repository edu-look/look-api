package com.github.edulook.look.infra.repository.course.mapper;

import com.github.edulook.look.infra.repository.course.mapper.factories.FactoryCourseMaterial;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import static com.github.edulook.look.core.model.Course.WorkMaterial.Material;

@Component
@NoArgsConstructor
public class ClassroomMaterialToCourseMaterialMapper {
    public Material toModel(com.google.api.services.classroom.model.Material source) {
        var factory = FactoryCourseMaterial.create(source);
        return factory.create(source);
    }
}
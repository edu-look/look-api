package com.github.edulook.look.infra.repository.course.mapper.factories;

import com.github.edulook.look.core.data.FileType;
import com.github.edulook.look.core.model.Course;
import com.google.api.services.classroom.model.Material;

public class CourseMaterialFormFactory implements AbstractCourseMaterialFactory {
    @Override
    public Course.WorkMaterial.Material create(Material source) {
        if(source == null)
            throw new RuntimeException("Material can't be null");

        return Course.WorkMaterial.Material
            .builder()
            .originLink(source.getForm().getFormUrl())
            .type(FileType.FORM)
            .description(source.getForm().getTitle())
            .build();
    }
}

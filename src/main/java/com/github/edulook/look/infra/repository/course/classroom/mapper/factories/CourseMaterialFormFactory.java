package com.github.edulook.look.infra.repository.course.classroom.mapper.factories;

import com.github.edulook.look.core.data.PageContent;
import com.github.edulook.look.core.data.Typename;
import com.github.edulook.look.core.model.Course;
import com.google.api.services.classroom.model.Material;

import java.util.Collections;

public class CourseMaterialFormFactory implements AbstractCourseMaterialFactory {
    @Override
    public Course.WorkMaterial.Material create(Material source) {
        if(source == null)
            throw new IllegalArgumentException("Material can't be null");

        return Course.WorkMaterial.Material
            .builder()
            .id(hash256(source.getForm().getFormUrl()))
            .name(source.getForm().getTitle())
            .originLink(source.getForm().getFormUrl())
            .previewLink(source.getForm().getThumbnailUrl())
            .type(Typename.FORM)
            .description(PageContent.withDefaults(source.getForm().getTitle()))
            .build();
    }
}

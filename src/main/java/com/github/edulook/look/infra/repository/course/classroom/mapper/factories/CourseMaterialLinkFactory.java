package com.github.edulook.look.infra.repository.course.classroom.mapper.factories;

import com.github.edulook.look.core.data.Typename;
import com.github.edulook.look.core.model.Course;
import com.google.api.services.classroom.model.Material;

public class CourseMaterialLinkFactory implements AbstractCourseMaterialFactory {
    @Override
    public Course.WorkMaterial.Material create(Material source) {
        if(source == null)
            throw new IllegalArgumentException("Material can't be null");

        return Course.WorkMaterial.Material
                .builder()
                .originLink(source.getLink().getUrl())
                .previewLink(source.getLink().getThumbnailUrl())
                .type(Typename.WEB)
                .description(source.getDriveFile().getDriveFile().getTitle())
                .build();
    }
}

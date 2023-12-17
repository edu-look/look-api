package com.github.edulook.look.infra.repository.course.classroom.mapper.factories;

import com.github.edulook.look.core.data.Typename;
import com.github.edulook.look.core.model.Course;
import com.google.api.services.classroom.model.Material;

public class CourseMaterialYoutubeFactory implements AbstractCourseMaterialFactory {
    @Override
    public Course.WorkMaterial.Material create(Material source) {
        if(source == null)
            throw new IllegalArgumentException("Material can't be null");

        return Course.WorkMaterial.Material
            .builder()
            .originLink(source.getYoutubeVideo().getAlternateLink())
            .type(Typename.VIDEO)
            .description(source.getYoutubeVideo().getTitle())
            .build();
    }
}

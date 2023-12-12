package com.github.edulook.look.infra.repository.course.mapper.factories;

import com.github.edulook.look.core.data.FileType;
import com.github.edulook.look.core.model.Course;
import com.google.api.services.classroom.model.Material;

public class CourseMaterialYoutubeFactory implements AbstractCourseMaterialFactory {
    @Override
    public Course.WorkMaterial.Material create(Material source) {
        if(source == null)
            throw new RuntimeException("Material can't be null");

        return Course.WorkMaterial.Material
            .builder()
            .originLink(source.getYoutubeVideo().getAlternateLink())
            .type(FileType.VIDEO)
            .description(source.getYoutubeVideo().getTitle())
            .build();
    }
}

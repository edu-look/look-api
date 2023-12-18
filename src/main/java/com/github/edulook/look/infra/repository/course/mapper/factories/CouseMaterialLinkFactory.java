package com.github.edulook.look.infra.repository.course.mapper.factories;

import com.github.edulook.look.core.data.FileType;
import com.github.edulook.look.core.model.Course;
import com.google.api.services.classroom.model.Material;

public class CouseMaterialLinkFactory implements AbstractCourseMaterialFactory {
    @Override
    public Course.WorkMaterial.Material create(Material source) {
        return Course.WorkMaterial.Material.builder()
                .originLink(source.getLink().getUrl())
                .type(FileType.NONE)
                .description(source.getLink().getTitle())
                .build();
    }
}

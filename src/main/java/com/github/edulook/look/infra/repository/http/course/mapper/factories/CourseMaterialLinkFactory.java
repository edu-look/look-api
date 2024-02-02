package com.github.edulook.look.infra.repository.http.course.mapper.factories;

import com.github.edulook.look.core.data.Typename;
import com.google.api.services.classroom.model.Material;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CourseMaterialLinkFactory implements AbstractCourseMaterialFactory {
    @Override
    public com.github.edulook.look.core.model.Material create(Material source) {
        try {
            if (source == null)
                throw new IllegalArgumentException("material can't be null");

            var link = source.getLink();

            return com.github.edulook.look.core.model.Material
                .builder()
                .id(hash256(link.getUrl()))
                .name(normalizeFilename(link.getTitle()))
                .originLink(link.getUrl())
                .previewLink(link.getThumbnailUrl())
                .type(Typename.WEB)
                .description(link.getTitle())
                .build();
        } catch (Exception e) {
           log.error("error:: ", e);
           return null;
        }
    }
}

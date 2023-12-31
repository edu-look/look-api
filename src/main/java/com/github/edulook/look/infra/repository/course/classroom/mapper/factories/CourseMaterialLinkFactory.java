package com.github.edulook.look.infra.repository.course.classroom.mapper.factories;

import com.github.edulook.look.core.data.Option;
import com.github.edulook.look.core.data.PageContent;
import com.github.edulook.look.core.data.Range;
import com.github.edulook.look.core.data.Typename;
import com.github.edulook.look.core.model.Course;
import com.google.api.services.classroom.model.Material;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CourseMaterialLinkFactory implements AbstractCourseMaterialFactory {
    @Override
    public Course.WorkMaterial.Material create(Material source) {
        try {
            if (source == null)
                throw new IllegalArgumentException("material can't be null");

            var link = source.getLink();

            return Course.WorkMaterial.Material
                .builder()
                .id(hash256(link.getUrl()))
                .name(normalizeFilename(link.getTitle()))
                .originLink(link.getUrl())
                .previewLink(link.getThumbnailUrl())
                .type(Typename.WEB)
                .description(link.getTitle())
                .option(Option.None())
                .content(PageContent.None())
                .build();
        } catch (Exception e) {
           log.error("error:: ", e);
           return null;
        }
    }
}

package com.github.edulook.look.infra.repository.http.course.mapper.factories;

import com.github.edulook.look.core.data.Option;
import com.github.edulook.look.core.data.PageContent;
import com.github.edulook.look.core.data.Typename;
import com.github.edulook.look.core.model.Course;
import com.google.api.services.classroom.model.Material;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CourseMaterialFormFactory implements AbstractCourseMaterialFactory {
    @Override
    public Course.WorkMaterial.Material create(Material source) {
        try {
            if(source == null)
                throw new IllegalArgumentException("material can't be null");

            var form = source.getForm();

            return Course.WorkMaterial.Material
                .builder()
                .id(hash256(form.getFormUrl()))
                .name(form.getTitle())
                .originLink(form.getFormUrl())
                .previewLink(form.getThumbnailUrl())
                .type(Typename.FORM)
                .description(form.getTitle())
                .option(Option.None())
                .content(PageContent.None())
                .build();
        }
        catch (Exception e) {
            log.error("error:: ", e);
            return null;
        }
    }
}

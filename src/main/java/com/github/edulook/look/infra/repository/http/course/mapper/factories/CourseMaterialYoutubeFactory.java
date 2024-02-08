package com.github.edulook.look.infra.repository.http.course.mapper.factories;

import com.github.edulook.look.core.data.Typename;
import com.google.api.services.classroom.model.Material;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CourseMaterialYoutubeFactory implements AbstractCourseMaterialFactory {
    @Override
    public com.github.edulook.look.core.model.Material create(Material source) {
        try {
            if(source == null)
                throw new IllegalArgumentException("material can't be null");

            var video = source.getYoutubeVideo();

            return com.github.edulook.look.core.model.Material
                .builder()
                .id(hash256(video.getAlternateLink()))
                .name(video.getTitle())
                .originLink(video.getAlternateLink())
                .type(Typename.VIDEO)
                .description(video.getTitle())
                .build();
        } catch (Exception e) {
            log.error("error:: ", e);
            return null;
        }
    }
}

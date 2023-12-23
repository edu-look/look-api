package com.github.edulook.look.infra.repository.course.classroom.mapper.factories;

import com.github.edulook.look.core.data.PageContent;
import com.github.edulook.look.core.data.Range;
import com.github.edulook.look.core.data.Typename;
import com.github.edulook.look.core.model.Course;
import com.google.api.services.classroom.model.Material;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.codec.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;

@Slf4j
public class CourseMaterialYoutubeFactory implements AbstractCourseMaterialFactory {
    @Override
    public Course.WorkMaterial.Material create(Material source) {
        try {
            if(source == null)
                throw new IllegalArgumentException("material can't be null");

            var video = source.getYoutubeVideo();

            return Course.WorkMaterial.Material
                .builder()
                .id(hash256(video.getAlternateLink()))
                .name(video.getTitle())
                .originLink(video.getAlternateLink())
                .type(Typename.VIDEO)
                .description(video.getTitle())
                .range(Range.None())
                .content(PageContent.None())
                .build();
        } catch (Exception e) {
            log.error("error:: ", e);
            return null;
        }
    }
}

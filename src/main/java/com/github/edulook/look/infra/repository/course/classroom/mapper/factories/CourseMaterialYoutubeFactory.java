package com.github.edulook.look.infra.repository.course.classroom.mapper.factories;

import com.github.edulook.look.core.data.PageContent;
import com.github.edulook.look.core.data.Typename;
import com.github.edulook.look.core.model.Course;
import com.google.api.services.classroom.model.Material;
import org.springframework.security.crypto.codec.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;

public class CourseMaterialYoutubeFactory implements AbstractCourseMaterialFactory {
    @Override
    public Course.WorkMaterial.Material create(Material source) {
        if(source == null)
            throw new IllegalArgumentException("Material can't be null");

        return Course.WorkMaterial.Material
            .builder()
            .id(hash256(source.getYoutubeVideo().getAlternateLink()))
            .name(source.getYoutubeVideo().getTitle())
            .originLink(source.getYoutubeVideo().getAlternateLink())
            .type(Typename.VIDEO)
            .description(PageContent.withDefaults(source.getYoutubeVideo().getTitle()))
            .build();
    }
}

package com.github.edulook.look.infra.repository.http.course.mapper.factories;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.codec.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import static com.github.edulook.look.core.model.Course.WorkMaterial.Material;


public interface AbstractCourseMaterialFactory {
    Material create(com.google.api.services.classroom.model.Material source);

    default String hash256(String source) {
        try {
            var digest = MessageDigest.getInstance("SHA-256");
            var hash = digest.digest(source.getBytes(StandardCharsets.UTF_8));
            return new String(Hex.encode(hash));

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    default String normalizeFilename(String filename) {

        try {
            var slices = filename.split("[.]");
            var rawFilename  = slices[0].toLowerCase(Locale.ROOT);

            var cleanFilename = rawFilename.trim()
                    .replaceAll("[-_.]", " ")
                    .replaceAll(" +", " ");

            return StringUtils.capitalize(cleanFilename);
        }
        catch (Exception e) {
            return filename;
        }
    }
}

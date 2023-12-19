package com.github.edulook.look.infra.repository.course.classroom.mapper.factories;

import com.github.edulook.look.core.data.Typename;
import com.github.edulook.look.core.model.Course;
import org.springframework.security.crypto.codec.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
}

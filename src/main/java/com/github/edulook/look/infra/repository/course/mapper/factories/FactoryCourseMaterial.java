package com.github.edulook.look.infra.repository.course.mapper.factories;

import com.google.api.services.classroom.model.Material;
import org.apache.commons.lang3.NotImplementedException;

public class FactoryCourseMaterial {
    public static AbstractCourseMaterialFactory create(Material source) {
        if (source == null)
            throw new RuntimeException("Material can't be null");

        if(source.getYoutubeVideo() != null)
            return new CourseMaterialYoutubeFactory();
        if(source.getDriveFile() != null)
            return new CourseMaterialGDriveFactory();
        if(source.getForm() != null)
            return new CourseMaterialFormFactory();

        throw new NotImplementedException();
    }
}

package com.github.edulook.look.infra.repository.http.course.mapper.factories;

import com.google.api.services.classroom.model.Material;
import org.apache.commons.lang3.NotImplementedException;

public class FactoryCourseMaterial {
    public static AbstractCourseMaterialFactory create(Material source) {
        if (source == null)
            throw new IllegalArgumentException("Material can't be null");

        if(source.getYoutubeVideo() != null)
            return new CourseMaterialYoutubeFactory();
        if(source.getDriveFile() != null)
            return new CourseMaterialGDriveFactory();
        if(source.getForm() != null)
            return new CourseMaterialFormFactory();
        if(source.getLink() != null)
            return new CourseMaterialLinkFactory();

        throw new NotImplementedException();
    }
}

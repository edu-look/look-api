package com.github.edulook.look.infra.repository.course.classroom.mapper.factories;

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
<<<<<<< HEAD:src/main/java/com/github/edulook/look/infra/repository/course/mapper/factories/FactoryCourseMaterial.java
            return new CouseMaterialLinkFactory();
=======
            return new CourseMaterialLinkFactory();
>>>>>>> 17c1f651d2fb525063fbde8dccccda73e241afde:src/main/java/com/github/edulook/look/infra/repository/course/classroom/mapper/factories/FactoryCourseMaterial.java

        throw new NotImplementedException();
    }
}

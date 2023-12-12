package com.github.edulook.look.infra.repository.course.mapper.factories;

import static com.github.edulook.look.core.model.Course.WorkMaterial.Material;

public interface AbstractCourseMaterialFactory {
    Material create(com.google.api.services.classroom.model.Material source);
}

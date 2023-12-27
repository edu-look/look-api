package com.github.edulook.look.infra.worker.events.course;

import lombok.Builder;
import lombok.Getter;



import java.util.List;


import static com.github.edulook.look.core.model.Course.WorkMaterial;
import static com.github.edulook.look.core.model.Course.WorkMaterial.Material;

@Getter
@Builder
public class WorkMaterialEvent {
    private String id;
    private String courseId;
    private String title;
    private String description;
    private String createdAt;
    private List<Material> materials;


    public static WorkMaterialEvent fromModel(WorkMaterial workMaterial) {
        return WorkMaterialEvent
            .builder()
            .id(workMaterial.getId())
            .courseId(workMaterial.getCourseId())
            .title(workMaterial.getTitle())
            .description(workMaterial.getDescription())
            .createdAt(workMaterial.getCreatedAt())
            .materials(workMaterial.getMaterials())
            .build();
    }
}

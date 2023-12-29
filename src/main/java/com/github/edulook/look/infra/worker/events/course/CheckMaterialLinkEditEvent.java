package com.github.edulook.look.infra.worker.events.course;

import com.github.edulook.look.core.model.Course.WorkMaterial;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
public class CheckMaterialLinkEditEvent {
    private UUID id;
    private WorkMaterial material;
    private String courseId;
    private String materialId;
}

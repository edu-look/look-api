package com.github.edulook.look.endpoint.io.course;


import lombok.Builder;
import lombok.Getter;

@Builder
public record SimpleMaterialDTO(
    String id,
    String courseId,
    String title,
    String description,
    String createdAt

) {
}

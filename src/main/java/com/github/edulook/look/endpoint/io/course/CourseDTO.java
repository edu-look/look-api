package com.github.edulook.look.endpoint.io.course;

import lombok.Builder;

@Builder
public record CourseDTO(
    String id,
    String name,
    String description,
    String ownerEmail,
    String room,
    String courseState
) {
}

package com.github.edulook.look.endpoint.io.teacher;

import lombok.Builder;

@Builder
public record TeacherDTO(
    String name,
    String id,
    String photo,
    String email
) {
}

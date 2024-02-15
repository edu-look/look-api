package com.github.edulook.look.endpoint.io.course;

import lombok.Builder;

@Builder
public record AnnouncementDTO(
    String id,
    String content,
    String createdAt,
    String owner
) {
}

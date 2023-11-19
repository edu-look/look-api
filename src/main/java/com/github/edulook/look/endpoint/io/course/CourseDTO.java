package com.github.edulook.look.endpoint.io.course;

import java.util.List;

import lombok.Builder;

@Builder
public record CourseDTO(
    String id,
    String name,
    String description,
    String owner,
    String room,
    String state,
    List<TeacherDTO> teachers
) {

    @Builder
    public static record TeacherDTO(
        String name,
        String id,
        String photo,
        String email
    ) {
    }

}

package com.github.edulook.look.endpoint.io.course;

import java.util.List;

import com.github.edulook.look.endpoint.io.course.CourseDTO.TeacherDTO;

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

    @Builder
    public static record AnnouncementDTO(
        String id,
        String content,
        String createdAt,
        String owner
    ) {
    }
}

package com.github.edulook.look.endpoint.io.course;

import java.util.List;

import com.github.edulook.look.endpoint.io.teacher.TeacherDTO;
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

}

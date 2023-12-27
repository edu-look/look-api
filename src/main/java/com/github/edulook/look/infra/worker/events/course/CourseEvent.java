package com.github.edulook.look.infra.worker.events.course;

import com.github.edulook.look.core.model.Course;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class CourseEvent {

    private UUID id;
    private Course course;
}

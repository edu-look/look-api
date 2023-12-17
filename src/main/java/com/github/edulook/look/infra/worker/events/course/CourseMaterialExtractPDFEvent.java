package com.github.edulook.look.infra.worker.events.course;

import com.github.edulook.look.core.data.Range;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CourseMaterialExtractPDFEvent {

    private String courseId;
    private String materialId;
    private String contentId;
    private Range range;
}

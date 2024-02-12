package com.github.edulook.look.infra.worker.events.course;

import com.github.edulook.look.core.data.Option;
import com.github.edulook.look.infra.worker.events.AbstractEvent;
import com.github.edulook.look.infra.tools.PDFClassificationEnum;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Getter
@Builder
public class CourseMaterialExtractPDFEvent extends AbstractEvent {

    private String courseId;
    private String materialId;
    private String contentId;
    private Option option;
    private PDFClassificationEnum classification;

    public boolean isValid() {
        var nullNotPresent = Stream.of(courseId, materialId, contentId, option)
            .filter(Objects::isNull)
            .toList()
            .isEmpty();

        return nullNotPresent && option.isValid();
    }

    public boolean isNotValid() {
        return !isValid();
    }
}

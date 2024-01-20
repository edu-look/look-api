package com.github.edulook.look.infra.worker.events.course;

import com.github.edulook.look.core.data.Option;
import com.github.edulook.look.infra.worker.events.AbstractEvent;
import com.github.edulook.look.infra.tools.PDFClassificationEnum;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
@Builder
public class CourseMaterialExtractPDFEvent extends AbstractEvent {

    private String courseId;
    private String materialId;
    private String contentId;
    private Option option;
    private PDFClassificationEnum classification;

    public boolean isValid() {
        var noContainsNull = Objects.nonNull(courseId)
            && Objects.nonNull(materialId)
            && Objects.nonNull(contentId)
            && Objects.nonNull(option)
            && Objects.nonNull(classification);

        return noContainsNull && option.getRange().isValid();
    }

    public boolean isNotValid() {
        return !isValid();
    }
}

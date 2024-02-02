package com.github.edulook.look.core.usecase;

import com.github.edulook.look.core.model.PageContent;
import com.github.edulook.look.core.data.Range;

import java.io.File;
import java.util.Optional;

public interface PDFContentExtractor {
    Optional<PageContent> extract(File pdf, Range range);

    default Boolean isRangeWithinPages(Range range, Integer pageSize) {
        return range.isValid() && range.getStartPosition() <= pageSize && range.getEndPosition() <= pageSize ;
    }
}

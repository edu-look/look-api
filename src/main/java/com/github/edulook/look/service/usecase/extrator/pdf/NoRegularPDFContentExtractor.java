package com.github.edulook.look.service.usecase.extrator.pdf;

import com.github.edulook.look.core.data.PageContent;
import com.github.edulook.look.core.data.Range;
import com.github.edulook.look.core.usecase.PDFContentExtractor;

import java.io.File;
import java.util.Optional;

public class NoRegularPDFContentExtractor implements PDFContentExtractor {

    @Override
    public Optional<PageContent> extract(File pdf, Range range) {
        return Optional.empty();
    }
}

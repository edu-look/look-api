package com.github.edulook.look.core.usecase;

import com.github.edulook.look.core.data.Option;
import com.github.edulook.look.core.data.PageContent;
import com.github.edulook.look.core.data.Range;

import java.io.File;
import java.util.Optional;

public interface PDFContentExtractor {
    Optional<PageContent> extract(File pdf, Range range);
}

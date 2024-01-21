package com.github.edulook.look.infra.tools;

import com.github.edulook.look.core.data.PageContent;
import com.github.edulook.look.core.data.Range;
import com.github.edulook.look.core.exceptions.TextExtractInvalidException;
import com.github.edulook.look.core.usecase.PDFContentExtractor;
import org.apache.pdfbox.io.RandomAccessReadBufferedFile;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import static org.apache.pdfbox.Loader.*;

@Component
public class RegularPDFContentExtractor implements PDFContentExtractor {
    @Override
    public Optional<PageContent> extract(File pdf, Range range) {
        try (var document = loadPDF(new RandomAccessReadBufferedFile(pdf))) {
            var stripper = new PDFTextStripper();
            var pages = new ArrayList<PageContent.Page>();

            if(!isRangeWithinPages(range, document.getNumberOfPages()))
                throw new TextExtractInvalidException();

            for (var currentPage = range.getStart(); currentPage <= range.getEnd(); currentPage++) {
                stripper.setStartPage(range.getStart());
                stripper.setEndPage(range.getEnd());

                var page = PageContent.Page.builder()
                    .page(currentPage)
                    .content(stripper.getText(document))
                    .build();
                pages.add(page);
            }

            return Optional.of(PageContent.builder()
                .pages(pages)
                .size(pages.size())
                .build());

        } catch (IOException e) {
            return Optional.empty();
        }
    }
}

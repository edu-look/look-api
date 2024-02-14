package com.github.edulook.look.infra.tools;

import com.github.edulook.look.core.model.PageContent;
import com.github.edulook.look.core.data.Range;
import com.github.edulook.look.core.exceptions.TextExtractInvalidException;
import com.github.edulook.look.core.model.Page;
import com.github.edulook.look.core.usecase.PDFContentExtractor;
import org.apache.pdfbox.io.RandomAccessReadBufferedFile;
import org.apache.pdfbox.pdmodel.PDDocument;
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
            var pages = new ArrayList<Page>();

            if(!isRangeWithinPages(range, document.getNumberOfPages()))
                throw new TextExtractInvalidException();

            for (var currentPage = range.getStartPosition(); currentPage <= range.getEndPosition(); currentPage++) {
                pages.add(extractPageContent(range, currentPage, document));
            }

            return Optional.of(PageContent.builder()
                .pages(pages)
                .size(pages.size())
                .build());

        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private static Page extractPageContent(Range range, Integer currentPage, PDDocument document) throws IOException {
        var stripper = new PDFTextStripper();
        stripper.setStartPage(range.getStartPosition());
        stripper.setEndPage(range.getEndPosition());

        return Page.builder()
            .page(currentPage)
            .content(stripper.getText(document))
            .build();
    }
}

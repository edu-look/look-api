package com.github.edulook.look.infra.tools;

import com.github.edulook.look.core.model.PageContent;
import com.github.edulook.look.core.model.Page;
import com.github.edulook.look.core.data.Range;
import com.github.edulook.look.core.exceptions.TextExtractInvalidException;
import com.github.edulook.look.core.usecase.PDFContentExtractor;
import com.github.edulook.look.utils.LookUtils;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.io.RandomAccessReadBufferedFile;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.apache.pdfbox.Loader.loadPDF;

@Slf4j
public class NoRegularPDFContentExtractor implements PDFContentExtractor {
    private final String WORK_DIR = "./data";
    private final String LANGUAGE = "por";
    private final int TESSERACT_ENGINE_MODE = 1;
    private final int TESSERACT_PAGE_SEG_MODE = 1;

    @Override
    public Optional<PageContent> extract(File pdf, Range range) {
        if(range.isNotValid()) {
            log.error("range::end or range:start  not is valid: {}", range);
            return Optional.empty();
        }

        LookUtils.mkdir(WORK_DIR);

        try (var document = loadPDF(new RandomAccessReadBufferedFile(pdf))) {
            var pdfSlices = splitPDFFile(pdf, range, document);
            var pages = extractPagesContent(pdfSlices);

            return Optional.of(PageContent.builder()
                .pages(pages)
                .size(pdfSlices.size())
                .build());
        } catch (TextExtractInvalidException | TesseractException | IOException e) {
            log.error("error:: ", e);
            return Optional.empty();
        }
    }

    private List<Page> extractPagesContent(List<ContainerPDFRef> pages) throws TesseractException {
        final Tesseract tesseract = new Tesseract() {{
            setLanguage(LANGUAGE);
            setPageSegMode(TESSERACT_PAGE_SEG_MODE);
            setOcrEngineMode(TESSERACT_ENGINE_MODE);
            setDatapath(WORK_DIR);
        }};

        return pages
            .stream()
            .map(it -> {
                var page = new File(it.filename());
                try {
                    return Page.builder()
                        .page(it.index())
                        .content(tesseract.doOCR(page))
                        .build();
                } catch (TesseractException e) {
                    return null;
                } finally {
                    if(page.exists() && page.delete()) {
                        log.info("removed tmp page");
                    };
                }
            })
            .filter(Objects::nonNull)
            .toList();
    }

    private List<ContainerPDFRef> splitPDFFile(File pdf, Range range, PDDocument document) throws IOException {
        var pages = new ArrayList<ContainerPDFRef>();
        var splitter = new Splitter();
        var slices = splitter.split(document);

        if(!isRangeWithinPages(range, document.getNumberOfPages()))
            throw new TextExtractInvalidException();

        var extension = FilenameUtils.getExtension(pdf.getName());
        var filename = FilenameUtils.getName(pdf.getName());

        for (var currentPage = range.getStartPosition(); currentPage <= range.getEndPosition(); currentPage++) {
            var pdfPage = slices.get(currentPage-1);
            var pageName = String.format("%s/%s-%s.%s", WORK_DIR, filename, currentPage, extension);
            pages.add(new ContainerPDFRef(currentPage, pageName));
            pdfPage.save(pageName);
        }
        return pages;
    }

    private record ContainerPDFRef(
      Integer index,
      String filename
    ){}
}

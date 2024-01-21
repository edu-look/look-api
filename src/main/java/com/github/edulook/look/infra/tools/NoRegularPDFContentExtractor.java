package com.github.edulook.look.infra.tools;

import com.github.edulook.look.core.data.PageContent;
import com.github.edulook.look.core.data.PageContent.Page;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.apache.pdfbox.Loader.loadPDF;

@Slf4j
@Component
public class NoRegularPDFContentExtractor implements PDFContentExtractor {
    @Value("${look.application.language:./data}")
    private String workDir;
    @Value("${look.application.data:./por}")
    private String language;

    @Override
    public Optional<PageContent> extract(File pdf, Range range) {
        if(range.isNotValid()) {
            log.error("range::end or range:start  not is valid: {}", range);
            return Optional.empty();
        }

        LookUtils.mkdir(workDir);

        try (var document = loadPDF(new RandomAccessReadBufferedFile(pdf))) {
            var pdfSlices = splitPDFFile(pdf, range, document);
            var pages = extractContent(pdfSlices);

            return Optional.ofNullable(PageContent.builder()
                .pages(pages)
                .size(pdfSlices.size())
                .build());
        } catch (TextExtractInvalidException | TesseractException | IOException e) {
            log.error("error:: ", e);
            return Optional.empty();
        }
    }

    private List<Page> extractContent(List<ContainerPDFRef> pdfSlices) throws TesseractException {
        var pages = new ArrayList<Page>();

        for(var slice : pdfSlices) {
            var file = new File(slice.filename());
            var tesseract = new Tesseract() {{
               setLanguage(language);
               setPageSegMode(1);
               setOcrEngineMode(1);
            }};

            pages.add(Page.builder()
                .page(slice.index())
                .content(tesseract.doOCR(file))
                .build()
            );

            if(file.delete()) {
                log.info("removing tmp page");
            };
        }
        return pages;
    }

    private List<ContainerPDFRef> splitPDFFile(File pdf, Range range, PDDocument document) throws IOException {
        var pages = new ArrayList<ContainerPDFRef>();
        var splitter = new Splitter();
        var slices = splitter.split(document);

        if(!isRangeWithinPages(range, document.getNumberOfPages()))
            throw new TextExtractInvalidException();

        var extension = FilenameUtils.getExtension(pdf.getName());
        var filename = FilenameUtils.getName(pdf.getName());

        for (var currentPage = range.getStart(); currentPage <= range.getEnd(); currentPage++) {
            var pdfPage = slices.get(currentPage-1);
            var pageName = String.format("%s/%s-%s.%s", workDir, filename, currentPage, extension);
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

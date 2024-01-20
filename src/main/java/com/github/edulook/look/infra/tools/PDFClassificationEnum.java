package com.github.edulook.look.infra.tools;

import com.github.edulook.look.core.usecase.PDFContentExtractor;

public enum PDFClassificationEnum {
    REGULAR {
        @Override
        public PDFContentExtractor instance() {
            return new RegularPDFContentExtractor();
        }
    },
    NOREGULAR {
        @Override
        public PDFContentExtractor instance() {
            return new NoRegularPDFContentExtractor();
        }
    };

    public abstract PDFContentExtractor instance();
}

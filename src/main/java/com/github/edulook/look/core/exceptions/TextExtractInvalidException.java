package com.github.edulook.look.core.exceptions;

import java.io.IOException;

public class TextExtractInvalidException extends RuntimeException {
    public TextExtractInvalidException(String message) {
        super(message);
    }

    public TextExtractInvalidException(IOException e) {
    }
}

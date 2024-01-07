package com.github.edulook.look.core.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
public class Option {
    private boolean enableOCR;
    private Range range;

    public static Option withDefaults() {
        return new Option(Boolean.FALSE, Range.withDefaults());
    }
}
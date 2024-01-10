package com.github.edulook.look.core.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Option {
    private boolean enableOCR;
    private Range range;

    public static Option withDefaults() {
        return new Option(Boolean.FALSE, Range.withDefaults());
    }

    public static Optional<Option> None() {
        return Optional.empty();
    }
}
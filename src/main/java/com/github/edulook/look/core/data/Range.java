package com.github.edulook.look.core.data;

import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Builder
@Getter
public class Range {
    private Integer start;
    private Integer end;

    public Range(Integer start, Integer end) {
        if(start == null || end == null)
            throw new IllegalArgumentException("Range::start or Range::end can't be null");

        if(start > end)
            throw new IllegalArgumentException("Range::end should be major or equals than Range::start");

        this.start = start;
        this.end = end;
    }

    public static Range withDefaults() {
        return new Range(0, 0);
    }
    public static Optional<Range> None() {
        return Optional.empty();
    }
}

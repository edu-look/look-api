package com.github.edulook.look.core.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Builder
@Getter
@NoArgsConstructor
public class Range {
    private Integer startPosition;
    private Integer endPosition;

    public Range(Integer startPosition, Integer endPosition) {
        if(startPosition == null || endPosition == null)
            throw new IllegalArgumentException("Range::start or Range::end can't be null");

        if(startPosition > endPosition)
            throw new IllegalArgumentException("Range::end should be major or equals than Range::start");

        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    public static Range withDefaults() {
        return new Range(0, 0);
    }
    public static Optional<Range> none() {
        return Optional.empty();
    }

    public boolean isValid() {
        return startPosition > 0 && endPosition > 0 && startPosition <= endPosition;
    }

    @JsonIgnore
    public boolean isNotValid() {
        return !isValid();
    }
}

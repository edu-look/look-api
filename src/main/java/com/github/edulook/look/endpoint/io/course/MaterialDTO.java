package com.github.edulook.look.endpoint.io.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.edulook.look.core.data.PageContent;
import com.github.edulook.look.core.data.Range;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Builder
public record MaterialDTO(
    @NotBlank
    String description,
    List<ContentMaterialDTO> materials
){
    @Builder
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public record ContentMaterialDTO(
        @NotBlank
        String id,
        @NotBlank
        String name,
        @NotBlank
        PageContent description,

        String origin,
        String preview,
        Range range
    ) {
    }
}

package com.github.edulook.look.endpoint.io.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.edulook.look.core.data.Option;
import com.github.edulook.look.core.data.PageContent;
import com.github.edulook.look.core.data.Range;
import lombok.Builder;

import java.util.List;
import java.util.Optional;

@Builder
public record MaterialDTO(
//    @NotBlank
    String description,
    List<ContentMaterialDTO> materials
){
    @Builder
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public record ContentMaterialDTO(
        String id,
        String name,
        String description,

        String origin,
        String preview,
        Optional<PageContent> content,
        Optional<Option> option
    ) {
    }
}

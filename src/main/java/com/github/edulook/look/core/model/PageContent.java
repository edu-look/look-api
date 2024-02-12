package com.github.edulook.look.core.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Optional;

@Entity
@Builder
@Data
@NoArgsConstructor
public class PageContent {
    @Id
    private Long id;
    private Integer size;

    @OneToMany(mappedBy = "pageContent")
    private List<Page> pages;

    public static PageContent fromCollection(List<Page> pages) {
        int size = pages.size();
        return new PageContent(null, size, pages);
    }

    private PageContent(Long id, Integer size, List<Page> pages) {
        this.id = id;
        this.size = size;
        this.pages = pages;
    }

    public static Optional<PageContent> none() {
        return Optional.empty();
    }
}

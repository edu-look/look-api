package com.github.edulook.look.core.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Builder
@Getter
public class PageContent {
    private Integer size;
    private List<Page> pages;

    public static PageContent withDefaults(String content) {
        return new PageContent(1, Collections.singletonList(new Page(1, content)));
    }

    public PageContent fromCollection(List<Page> pages) {
        return new PageContent(pages.size(), pages);
    }

    private PageContent(Integer size, List<Page> pages) {
        this.size = size;
        this.pages = pages;
    }


    public PageContent None () {
        return null;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Page {
        Integer page;
        String content;
    }
}

package com.fms.paginator.models;

import org.springframework.data.domain.Page;

public class PagedResponse<T> {
    private final Page<T> page;

    public PagedResponse(Page<T> page) {
        this.page = page;
    }

    public int getCurrentPage() {
        return page.getNumber();
    }

    public int getTotalPages() {
        return page.getTotalPages();
    }

    public long getTotalElements() {
        return page.getTotalElements();
    }

    public boolean hasContent() {
        return page.hasContent();
    }

    public Iterable<T> getContent() {
        return page.getContent();
    }

    // Other getters and methods as needed

    public static <T> PagedResponse<T> fromPage(Page<T> page) {
        return new PagedResponse<>(page);
    }
}

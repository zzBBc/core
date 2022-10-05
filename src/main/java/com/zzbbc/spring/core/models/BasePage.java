package com.zzbbc.spring.core.models;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import lombok.Data;

@Data
public class BasePage<T> {
    private final long totalElements;
    private final long totalPages;
    private final Integer pageNumber;
    private final Integer pageSize;
    private final List<T> content;

    public BasePage(List<T> content, Pageable pageable, long totalElements) {
        this(content, pageable.getPageNumber(), pageable.getPageSize(), totalElements);
    }

    public BasePage(List<T> content, Integer pageNumber, Integer pageSize, long totalElements) {
        this.totalElements = totalElements;
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPages = (int) Math.ceil(totalElements / pageSize.doubleValue());

    }

    public <U> BasePage<U> map(Function<? super T, ? extends U> converter) {
        List<U> mapper = this.content.stream().map(converter).collect(Collectors.toList());

        return new BasePage<>(mapper, this.pageNumber, this.pageSize, totalElements);
    }

}

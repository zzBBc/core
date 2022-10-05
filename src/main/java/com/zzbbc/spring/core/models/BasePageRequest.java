package com.zzbbc.spring.core.models;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class BasePageRequest extends PageRequest {
    private final int pageSize;

    protected BasePageRequest(int page, int size, Sort sort) {
        super(page <= 0 ? 1 : page, size, sort);

        this.pageSize = size;
    }

    @Override
    public long getOffset() {
        return super.getOffset() - this.pageSize;
    }

    public static BasePageRequest of(int page, int size) {
        return new BasePageRequest(page, size, Sort.unsorted());
    }
}

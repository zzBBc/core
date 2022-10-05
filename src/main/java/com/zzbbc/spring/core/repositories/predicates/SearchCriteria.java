package com.zzbbc.spring.core.repositories.predicates;

import java.util.Objects;
import com.zzbbc.spring.core.enums.FilterOperator;
import lombok.Getter;

@Getter
public class SearchCriteria {
    private final String key;
    private final FilterOperator operator;
    private final String value;

    public SearchCriteria(String key, String operator, String value) {
        this(key, FilterOperator.of(operator), value);
    }

    public SearchCriteria(String key, FilterOperator operator, String value) {
        if (Objects.isNull(operator)) {
            throw new IllegalArgumentException("Filter không hợp lệ: " + key + operator + value);
        }

        this.key = key;
        this.operator = operator;
        this.value = value;
    }
}

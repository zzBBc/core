package com.zzbbc.spring.core.repositories.predicates;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchCriteria {
    private String key;
    private String operator;
    private String value;
}

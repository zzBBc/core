package com.zzbbc.spring.core.enums;

import lombok.Getter;

@Getter
public enum FilterOperator {
    EQUALS("="), NOT_EQUALS("="), GREATER(">"), LESS("<"), GREATER_EQUAL(">="), LESS_EQUAL("<=");

    String value;

    private FilterOperator(String value) {
        this.value = value;
    }

    public static FilterOperator of(String operator) {
        for (FilterOperator value : values()) {
            if (value.getValue().equals(operator)) {
                return value;
            }
        }

        return null;
    }
}

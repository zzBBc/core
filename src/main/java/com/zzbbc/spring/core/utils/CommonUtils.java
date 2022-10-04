package com.zzbbc.spring.core.utils;

import java.util.Objects;
import java.util.function.Function;

public class CommonUtils {
    public static <T, R> R getOrDefault(T condition, Function<T, R> getter, R defaultValue) {
        return !isNull(condition) ? getter.apply(condition) : defaultValue;
    }

    public static boolean isNull(Object object) {
        return Objects.isNull(object);
    }
}

package com.zzbbc.spring.core.validators;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Validator<T> {
    private T data;

    private Validator(T data) {
        this.data = data;
    }

    public static <T> Validator<T> data(T data) {
        return new Validator<>(data);
    }

    public <R> Validator<T> validate(Function<T, R> map, Predicate<R> filter,
            Supplier<? extends RuntimeException> exception) {
        if (filter.test(map.apply(data)))
            throw exception.get();

        return this;
    }

    public <R> Validator<T> validate(Predicate<T> filter,
            Supplier<? extends RuntimeException> exception) {
        if (filter.test(data)) {
            throw exception.get();
        }

        return this;
    }


    public T getData() {
        return data;
    }
}

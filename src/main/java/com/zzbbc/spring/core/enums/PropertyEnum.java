package com.zzbbc.spring.core.enums;

public interface PropertyEnum<E extends Enum<?>> {
    E get(String name);

    String name();
}

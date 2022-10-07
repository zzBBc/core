package com.zzbbc.spring.core.enums;

public enum RoleEnum implements PropertyEnum<RoleEnum> {
    ROLE_USER, ROLE_MODERATOR, ROLE_ADMIN;

    @Override
    public RoleEnum get(String name) {
        for (RoleEnum e : RoleEnum.values()) {
            if (e.name().equals(name)) {
                return e;
            }
        }

        return null;
    }
}

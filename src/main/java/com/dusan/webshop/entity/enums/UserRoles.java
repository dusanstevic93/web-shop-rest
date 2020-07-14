package com.dusan.webshop.entity.enums;

import java.util.Arrays;

public enum UserRoles {
    CUSTOMER(1),
    ADMIN(2);

    private final long id;

    UserRoles(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public static UserRoles fromId(long id) {
        return Arrays.stream(values())
                .filter(role -> role.id == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Role with id " + id + " does not exist"));
    }
}

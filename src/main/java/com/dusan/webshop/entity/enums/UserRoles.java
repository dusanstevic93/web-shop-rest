package com.dusan.webshop.entity.enums;

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
}

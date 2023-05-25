package com.b2.bookingingorkutek.model;

import lombok.Generated;

@Generated
public enum ApplicationUserPermission {

    BOOKING_READ_ALL("booking:read-all"),
    BOOKING_READ_SELF("booking:read-self"),
    BOOKING_CREATE("booking:create"),
    BOOKING_UPDATE("booking:update"),
    BOOKING_DELETE("booking:delete"),
    GOR_READ("gor:read"),
    GOR_CREATE("gor:create"),
    GOR_UPDATE("gor:update"),
    GOR_DELETE("gor:delete"),
    REGISTER("auth:register"),
    LOGIN("auth:login");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}

package com.b2.bookingingorkutek.model;

public enum ApplicationUserPermission {

    BOOKING_READ_ALL(""),
    BOOKING_READ_SELF(""),
    BOOKING_CREATE(""),
    BOOKING_UPDATE(""),
    BOOKING_DELETE(""),
    GOR_READ(""),
    GOR_CREATE(""),
    GOR_UPDATE(""),
    GOR_DELETE(""),
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

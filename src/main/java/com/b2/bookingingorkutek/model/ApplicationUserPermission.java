package com.b2.bookingingorkutek.model;

public enum ApplicationUserPermission {
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

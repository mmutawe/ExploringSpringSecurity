package com.mmutawe.explore.spring.security.exploringspringsecurity.enums;

public enum AppPermission {
    CLIENT_READ("client:read"),
    CLIENT_WRITE("client:write"),
    PRODUCT_READ("product:read"),
    PRODUCT_WRITE("product:write");

    private final String permission;

    AppPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}

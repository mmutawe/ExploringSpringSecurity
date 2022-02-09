package com.mmutawe.explore.spring.security.exploringspringsecurity.enums;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.mmutawe.explore.spring.security.exploringspringsecurity.enums.AppPermission.*;

public enum ClientRole {
    USER(Sets.newHashSet()),
    MANAGER(Sets.newHashSet(CLIENT_READ, CLIENT_WRITE, PRODUCT_READ)),
    ADMIN(Sets.newHashSet(CLIENT_READ, CLIENT_WRITE, PRODUCT_READ));

    private final Set<AppPermission> permissions;

    ClientRole(Set<AppPermission> permissions) {
        this.permissions = permissions;
    }
}

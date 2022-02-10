package com.mmutawe.explore.spring.security.exploringspringsecurity.enums;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.mmutawe.explore.spring.security.exploringspringsecurity.enums.AppPermission.*;

public enum ClientRole {
    USER(Sets.newHashSet(CLIENT_READ)),
    MANAGER_TRAINEE(Sets.newHashSet(CLIENT_READ, PRODUCT_READ)),
    MANAGER(Sets.newHashSet(CLIENT_READ, CLIENT_WRITE, PRODUCT_READ)),
    ADMIN(Sets.newHashSet(CLIENT_READ, CLIENT_WRITE, PRODUCT_READ));

    private final Set<AppPermission> permissions;

    ClientRole(Set<AppPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<AppPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}

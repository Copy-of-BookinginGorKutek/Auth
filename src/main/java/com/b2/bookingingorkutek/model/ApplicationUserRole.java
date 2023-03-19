package com.b2.bookingingorkutek.model;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.b2.bookingingorkutek.model.ApplicationUserPermission.LOGIN;
import static com.b2.bookingingorkutek.model.ApplicationUserPermission.REGISTER;

public enum ApplicationUserRole {
    ADMIN(Sets.newHashSet(REGISTER, LOGIN)),
    USER(Sets.newHashSet(REGISTER, LOGIN));

    private final Set<ApplicationUserPermission> permissions;
    ApplicationUserRole(Set<ApplicationUserPermission> permissions){
        this.permissions = permissions;

    }
    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthority() {
        Set<SimpleGrantedAuthority> authorities = getPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+ this.name()));
        return authorities;
    }
}

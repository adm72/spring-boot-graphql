package ru.jbru.springbootgraphql.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public class CustomGrantedAuthority implements GrantedAuthority {
    private final UserRole userRole;
    private static final String PREFIX = "ROLE_";

    @Override
    public String getAuthority() {
        return PREFIX + userRole.name();
    }
}


package com.example.ss_2022_e2_c1.entities;
import org.springframework.security.core.GrantedAuthority;

public record SecurityAuthority(Authority auth) implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return auth.getName();
    }
}

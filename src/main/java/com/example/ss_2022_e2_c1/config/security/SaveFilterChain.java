package com.example.ss_2022_e2_c1.config.security;

import jakarta.servlet.FilterChain;

public class SaveFilterChain {

    private static FilterChain filterChain;

    public static FilterChain getFilterChain() {
        return filterChain;
    }

    public static void setFilterChain(FilterChain filterChain1) {
        filterChain = filterChain1;
    }
}

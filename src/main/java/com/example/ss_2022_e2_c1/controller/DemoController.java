package com.example.ss_2022_e2_c1.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@AllArgsConstructor
public class DemoController {
    @GetMapping("/demo")
    public Object hello() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}

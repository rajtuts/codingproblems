package com.example.jwtdemo.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class DemoController {

    /** Any authenticated user. */
    @GetMapping("/me")
    public Map<String, Object> me(Authentication authentication) {
        return Map.of(
                "username", authentication.getName(),
                "roles", authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).toList());
    }

    /** ROLE_ADMIN only (enforced in SecurityConfig). */
    @GetMapping("/admin/dashboard")
    public Map<String, String> adminDashboard(Authentication authentication) {
        return Map.of("message", "Welcome to the admin dashboard, " + authentication.getName());
    }
}

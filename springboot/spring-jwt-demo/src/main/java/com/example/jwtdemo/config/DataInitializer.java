package com.example.jwtdemo.config;

import com.example.jwtdemo.domain.AppUser;
import com.example.jwtdemo.domain.Role;
import com.example.jwtdemo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

/** Seeds two demo accounts. Remove this for anything beyond a demo. */
@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedUsers(UserRepository users, PasswordEncoder encoder) {
        return args -> {
            if (!users.existsByUsername("admin")) {
                users.save(new AppUser("admin", encoder.encode("admin12345"), Set.of(Role.ADMIN, Role.USER)));
            }
            if (!users.existsByUsername("user")) {
                users.save(new AppUser("user", encoder.encode("user12345"), Set.of(Role.USER)));
            }
        };
    }
}

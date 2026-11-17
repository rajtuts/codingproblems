package com.example.day5.config;

import com.example.day5.user.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;

@Configuration
public class DataSeeder {
    @Bean
    CommandLineRunner seedUsers(UserRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new User("Aarav Sharma", "aarav@example.com"));
                repository.save(new User("Diya Patel", "diya@example.com"));
                repository.save(new User("Ishaan Reddy", "ishaan@example.com"));
                repository.save(new User("Meera Nair", "meera@example.com"));
                repository.save(new User("Kabir Singh", "kabir@example.com"));
                repository.save(new User("Ananya Rao", "ananya@example.com"));
                repository.save(new User("Vihaan Kumar", "vihaan@example.com"));
                repository.save(new User("Sara Khan", "sara@example.com"));
            }
        };
    }
}

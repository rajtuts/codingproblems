package com.example.day6;
import org.springframework.boot.CommandLineRunner; import org.springframework.context.annotation.*;
@Configuration public class SeedData {
 @Bean CommandLineRunner seed(UserRepository r){return a->{if(r.count()==0){r.save(new AppUser("Aarav Sharma","aarav@example.com","ADMIN"));r.save(new AppUser("Diya Patel","diya@example.com","USER"));r.save(new AppUser("Ishaan Reddy","ishaan@example.com","USER"));r.save(new AppUser("Meera Nair","meera@example.com","USER"));r.save(new AppUser("Kabir Singh","kabir@example.com","USER"));r.save(new AppUser("Ananya Rao","ananya@example.com","USER"));r.save(new AppUser("Vihaan Kumar","vihaan@example.com","USER"));}};}
}

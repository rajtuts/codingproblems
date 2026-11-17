package com.example.day5.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtService jwtService;
    public AuthController(JwtService jwtService) { this.jwtService = jwtService; }

    public record LoginRequest(String username, String password) {}
    public record LoginResponse(String token) {}

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        if (!"admin".equals(request.username()) || !"password".equals(request.password())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
        return new LoginResponse(jwtService.generateToken(request.username()));
    }
}

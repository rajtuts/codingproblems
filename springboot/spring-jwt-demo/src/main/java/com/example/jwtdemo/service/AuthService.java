package com.example.jwtdemo.service;

import com.example.jwtdemo.domain.AppUser;
import com.example.jwtdemo.domain.Role;
import com.example.jwtdemo.dto.Dtos.AuthResponse;
import com.example.jwtdemo.dto.Dtos.LoginRequest;
import com.example.jwtdemo.dto.Dtos.RegisterRequest;
import com.example.jwtdemo.repository.RefreshTokenRepository;
import com.example.jwtdemo.repository.UserRepository;
import com.example.jwtdemo.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class AuthService {

    private final UserRepository users;
    private final RefreshTokenRepository refreshTokens;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository users,
                       RefreshTokenRepository refreshTokens,
                       RefreshTokenService refreshTokenService,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager,
                       PasswordEncoder passwordEncoder) {
        this.users = users;
        this.refreshTokens = refreshTokens;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (users.existsByUsername(request.username())) {
            throw new UsernameAlreadyExistsException(request.username());
        }
        AppUser user = users.save(new AppUser(
                request.username(),
                passwordEncoder.encode(request.password()),
                Set.of(Role.USER)));
        return issueTokens(user);
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        // Throws BadCredentialsException on a wrong username/password.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        AppUser user = users.findByUsername(request.username()).orElseThrow();
        return issueTokens(user);
    }

    /**
     * Refresh-token rotation:
     *  - a valid token is revoked and replaced by a brand-new pair;
     *  - presenting an already-revoked token means it was probably stolen, so ALL of that
     *    user's refresh tokens are revoked. noRollbackFor makes sure that revocation is committed
     *    even though we throw.
     */
    @Transactional(noRollbackFor = InvalidTokenException.class)
    public AuthResponse refresh(String rawRefreshToken) {
        var stored = refreshTokens.findByTokenHash(RefreshTokenService.hash(rawRefreshToken))
                .orElseThrow(() -> new InvalidTokenException("Invalid refresh token"));

        if (stored.isRevoked()) {
            refreshTokens.revokeAllForUser(stored.getUser());
            throw new InvalidTokenException("Refresh token reuse detected; all sessions revoked");
        }
        if (stored.isExpired()) {
            throw new InvalidTokenException("Refresh token expired");
        }

        stored.revoke();
        return issueTokens(stored.getUser());
    }

    @Transactional
    public void logout(String rawRefreshToken) {
        refreshTokens.findByTokenHash(RefreshTokenService.hash(rawRefreshToken))
                .ifPresent(t -> t.revoke());
    }

    private AuthResponse issueTokens(AppUser user) {
        var roles = user.getRoles().stream().map(r -> "ROLE_" + r.name()).toList();
        String access = jwtService.generateAccessToken(user.getUsername(), roles);
        String refresh = refreshTokenService.create(user);
        return new AuthResponse(access, refresh, "Bearer", jwtService.accessTokenTtlSeconds());
    }
}

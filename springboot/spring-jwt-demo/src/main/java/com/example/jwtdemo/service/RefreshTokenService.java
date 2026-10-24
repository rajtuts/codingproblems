package com.example.jwtdemo.service;

import com.example.jwtdemo.config.JwtProperties;
import com.example.jwtdemo.domain.AppUser;
import com.example.jwtdemo.domain.RefreshToken;
import com.example.jwtdemo.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.HexFormat;

/**
 * Refresh tokens are opaque random strings (not JWTs). Only their SHA-256 hash is stored,
 * so a database leak does not expose usable tokens.
 */
@Service
public class RefreshTokenService {

    private static final SecureRandom RANDOM = new SecureRandom();

    private final RefreshTokenRepository repository;
    private final JwtProperties props;

    public RefreshTokenService(RefreshTokenRepository repository, JwtProperties props) {
        this.repository = repository;
        this.props = props;
    }

    /** Persists a new refresh token and returns the RAW value (shown to the client exactly once). */
    public String create(AppUser user) {
        byte[] bytes = new byte[48];
        RANDOM.nextBytes(bytes);
        String raw = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);

        Instant expiresAt = Instant.now().plusMillis(props.refreshTokenExpirationMs());
        repository.save(new RefreshToken(user, hash(raw), expiresAt));
        return raw;
    }

    public static String hash(String raw) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(digest.digest(raw.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}

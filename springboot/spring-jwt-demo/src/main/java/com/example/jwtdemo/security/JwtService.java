package com.example.jwtdemo.security;

import com.example.jwtdemo.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/** Creates and validates short-lived access tokens (signed JWTs, HS256). */
@Service
public class JwtService {

    private static final String CLAIM_ROLES = "roles";
    private static final String CLAIM_TYPE = "type";
    private static final String TYPE_ACCESS = "access";

    private final JwtProperties props;
    private final SecretKey key;

    public JwtService(JwtProperties props) {
        this.props = props;
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(props.secret()));
    }

    public String generateAccessToken(String username, Collection<String> roles) {
        Instant now = Instant.now();
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(username)
                .claim(CLAIM_ROLES, roles)
                .claim(CLAIM_TYPE, TYPE_ACCESS)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(props.accessTokenExpirationMs())))
                .signWith(key)
                .compact();
    }

    /** Verifies signature + expiry and that this really is an access token. Throws JwtException otherwise. */
    public Claims parseAccessToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        if (!TYPE_ACCESS.equals(claims.get(CLAIM_TYPE, String.class))) {
            throw new JwtException("Not an access token");
        }
        return claims;
    }

    public List<String> extractRoles(Claims claims) {
        Object raw = claims.get(CLAIM_ROLES);
        if (raw instanceof List<?> list) {
            return list.stream().map(String::valueOf).toList();
        }
        return List.of();
    }

    public long accessTokenTtlSeconds() {
        return props.accessTokenExpirationMs() / 1000;
    }
}

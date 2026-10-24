package com.example.jwtdemo.domain;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "refresh_token")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** SHA-256 hash of the raw token. The raw token is never stored. */
    @Column(nullable = false, unique = true, length = 64)
    private String tokenHash;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private AppUser user;

    @Column(nullable = false)
    private Instant expiresAt;

    @Column(nullable = false)
    private boolean revoked = false;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    protected RefreshToken() {
    }

    public RefreshToken(AppUser user, String tokenHash, Instant expiresAt) {
        this.user = user;
        this.tokenHash = tokenHash;
        this.expiresAt = expiresAt;
    }

    public AppUser getUser() { return user; }
    public boolean isRevoked() { return revoked; }
    public boolean isExpired() { return expiresAt.isBefore(Instant.now()); }
    public void revoke() { this.revoked = true; }
}

# Spring Boot JWT demo (access + refresh tokens)

Java 17+, Maven 3.9+, Spring Boot 3.3, Spring Security 6.

## Run
    mvn spring-boot:run
    # tests
    mvn test

Set a real secret outside of dev:  `export JWT_SECRET=$(openssl rand -base64 32)`

## Seeded users
| username | password   | roles       |
|----------|------------|-------------|
| user     | user12345  | USER        |
| admin    | admin12345 | ADMIN, USER |

## Try it
    # login
    curl -s -X POST localhost:8080/api/auth/login -H 'Content-Type: application/json' \
      -d '{"username":"user","password":"user12345"}'

    # call a protected endpoint
    curl -s localhost:8080/api/me -H "Authorization: Bearer <accessToken>"

    # admin-only (403 for "user", 200 for "admin")
    curl -s localhost:8080/api/admin/dashboard -H "Authorization: Bearer <accessToken>"

    # get a new pair (old refresh token is revoked)
    curl -s -X POST localhost:8080/api/auth/refresh -H 'Content-Type: application/json' \
      -d '{"refreshToken":"<refreshToken>"}'

    # logout (revokes the refresh token)
    curl -s -X POST localhost:8080/api/auth/logout -H 'Content-Type: application/json' \
      -d '{"refreshToken":"<refreshToken>"}'

    # self-register (gets ROLE_USER)
    curl -s -X POST localhost:8080/api/auth/register -H 'Content-Type: application/json' \
      -d '{"username":"alice","password":"s3cretpass"}'

## Design
- **Access token**: signed JWT (HS256), 15 min, carries `sub` + `roles`; validated statelessly in
  `JwtAuthenticationFilter` (no DB hit per request).
- **Refresh token**: opaque 384-bit random string, 7 days, only its SHA-256 hash is stored.
- **Rotation**: every `/refresh` revokes the used token and issues a new pair.
- **Reuse detection**: presenting an already-used refresh token revokes all of that user's tokens.
- **Stateless**: `SessionCreationPolicy.STATELESS`, CSRF disabled (Bearer header, no cookies).

## Production hardening checklist
- Load `JWT_SECRET` from a secrets manager; consider RS256/ES256 if other services must verify tokens.
- Replace H2 with a real DB and Flyway/Liquibase; schedule cleanup of expired refresh tokens.
- Add rate limiting / lockout on `/login` and `/refresh`.
- For browser clients, consider delivering the refresh token in an HttpOnly, Secure, SameSite cookie
  (then re-enable CSRF protection for that endpoint).
- Remove `DataInitializer` and the open `/register` endpoint if not needed.
- Access tokens can't be revoked before expiry; keep them short or add a denylist keyed on the `jti` claim.

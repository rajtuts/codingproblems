# Idempotency table (Postgres)

## Table DDL

```sql
CREATE TABLE idempotency_keys (
  id BIGSERIAL PRIMARY KEY,
  idempotency_key TEXT NOT NULL,
  request_fingerprint TEXT NOT NULL,
  status TEXT NOT NULL,
  created_at TIMESTAMPTZ NOT NULL,
  updated_at TIMESTAMPTZ NOT NULL,
  expires_at TIMESTAMPTZ NOT NULL,
  response_body TEXT NULL,
  error_body TEXT NULL
);

-- Enforces one row per idempotency key.
CREATE UNIQUE INDEX ux_idempotency_keys_key ON idempotency_keys (idempotency_key);

-- Helps TTL cleanup.
CREATE INDEX ix_idempotency_keys_expires_at ON idempotency_keys (expires_at);
```

## Transactional flow

```sql
-- 1) Try to claim the key.
INSERT INTO idempotency_keys (
  idempotency_key, request_fingerprint, status, created_at, updated_at, expires_at
) VALUES (
  :key, :fingerprint, 'IN_PROGRESS', now(), now(), :expires_at
)
ON CONFLICT (idempotency_key) DO NOTHING;

-- 2) If inserted: run business logic, then persist result.
UPDATE idempotency_keys
SET status = 'COMPLETED', updated_at = now(), response_body = :response_body
WHERE idempotency_key = :key;

-- 3) If not inserted: lock and read existing row.
SELECT *
FROM idempotency_keys
WHERE idempotency_key = :key
FOR UPDATE;
```

## TTL cleanup job

Run periodically (e.g., every few minutes):

```sql
DELETE FROM idempotency_keys
WHERE expires_at < now();
```

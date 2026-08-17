package com.example.idempotency;

import java.time.Instant;

interface IdempotencyRepository {
  IdempotencyInsertResult tryInsertNew(
      String key,
      String requestFingerprint,
      Instant createdAt,
      Instant expiresAt,
      Status status
  );

  IdempotencyRecord findByKeyForUpdate(String key);

  void markCompleted(String key, Instant updatedAt, Instant expiresAt, String responseBody);

  void markFailed(String key, Instant updatedAt, Instant expiresAt, String errorBody);

  void deleteByKey(String key);

  int deleteExpired(Instant now);
}

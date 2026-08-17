package com.example.idempotency;

import java.time.Instant;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryIdempotencyRepository implements IdempotencyRepository {
  private final Map<String, IdempotencyRecord> table = new ConcurrentHashMap<>();

  @Override
  public IdempotencyInsertResult tryInsertNew(
      String key,
      String requestFingerprint,
      Instant createdAt,
      Instant expiresAt,
      Status status
  ) {
    Objects.requireNonNull(key);
    Objects.requireNonNull(requestFingerprint);
    Objects.requireNonNull(createdAt);
    Objects.requireNonNull(expiresAt);
    Objects.requireNonNull(status);

    IdempotencyRecord newRow = new IdempotencyRecord(
        key,
        requestFingerprint,
        status,
        createdAt,
        createdAt,
        expiresAt,
        null,
        null
    );

    IdempotencyRecord existing = table.putIfAbsent(key, newRow);
    return existing == null ? IdempotencyInsertResult.inserted() : IdempotencyInsertResult.notInserted();
  }

  @Override
  public IdempotencyRecord findByKeyForUpdate(String key) {
    IdempotencyRecord r = table.get(key);
    if (r == null) throw new IllegalStateException("Idempotency record not found for key=" + key);
    return r;
  }

  @Override
  public void markCompleted(String key, Instant updatedAt, Instant expiresAt, String responseBody) {
    table.compute(key, (k, old) -> {
      if (old == null) throw new IllegalStateException("Idempotency record not found for key=" + key);
      return old.withStatus(Status.COMPLETED)
          .withUpdatedAt(updatedAt)
          .withExpiresAt(expiresAt)
          .withResponseBody(responseBody)
          .withErrorBody(null);
    });
  }

  @Override
  public void markFailed(String key, Instant updatedAt, Instant expiresAt, String errorBody) {
    table.compute(key, (k, old) -> {
      if (old == null) throw new IllegalStateException("Idempotency record not found for key=" + key);
      return old.withStatus(Status.FAILED)
          .withUpdatedAt(updatedAt)
          .withExpiresAt(expiresAt)
          .withErrorBody(errorBody)
          .withResponseBody(null);
    });
  }

  @Override
  public void deleteByKey(String key) {
    table.remove(key);
  }

  @Override
  public int deleteExpired(Instant now) {
    int deleted = 0;
    Iterator<Map.Entry<String, IdempotencyRecord>> it = table.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry<String, IdempotencyRecord> e = it.next();
      if (e.getValue().expiresAt().isBefore(now)) {
        it.remove();
        deleted++;
      }
    }
    return deleted;
  }
}

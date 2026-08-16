package com.example.idempotency;

import java.time.Instant;
import java.util.Objects;

class IdempotencyRecord {
  private final String key;
  private final String requestFingerprint;
  private final Status status;
  private final Instant createdAt;
  private final Instant updatedAt;
  private final Instant expiresAt;
  private final String responseBody;
  private final String errorBody;

  IdempotencyRecord(
      String key,
      String requestFingerprint,
      Status status,
      Instant createdAt,
      Instant updatedAt,
      Instant expiresAt,
      String responseBody,
      String errorBody
  ) {
    this.key = Objects.requireNonNull(key);
    this.requestFingerprint = Objects.requireNonNull(requestFingerprint);
    this.status = Objects.requireNonNull(status);
    this.createdAt = Objects.requireNonNull(createdAt);
    this.updatedAt = Objects.requireNonNull(updatedAt);
    this.expiresAt = Objects.requireNonNull(expiresAt);
    this.responseBody = responseBody;
    this.errorBody = errorBody;
  }

  String key() {
    return key;
  }

  String requestFingerprint() {
    return requestFingerprint;
  }

  Status status() {
    return status;
  }

  Instant createdAt() {
    return createdAt;
  }

  Instant updatedAt() {
    return updatedAt;
  }

  Instant expiresAt() {
    return expiresAt;
  }

  String responseBody() {
    return responseBody;
  }

  String errorBody() {
    return errorBody;
  }

  IdempotencyRecord withStatus(Status s) {
    return new IdempotencyRecord(key, requestFingerprint, s, createdAt, updatedAt, expiresAt, responseBody, errorBody);
  }

  IdempotencyRecord withUpdatedAt(Instant t) {
    return new IdempotencyRecord(key, requestFingerprint, status, createdAt, t, expiresAt, responseBody, errorBody);
  }

  IdempotencyRecord withExpiresAt(Instant t) {
    return new IdempotencyRecord(key, requestFingerprint, status, createdAt, updatedAt, t, responseBody, errorBody);
  }

  IdempotencyRecord withResponseBody(String body) {
    return new IdempotencyRecord(key, requestFingerprint, status, createdAt, updatedAt, expiresAt, body, errorBody);
  }

  IdempotencyRecord withErrorBody(String body) {
    return new IdempotencyRecord(key, requestFingerprint, status, createdAt, updatedAt, expiresAt, responseBody, body);
  }
}

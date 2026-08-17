package com.example.idempotency;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

class IdempotencyService {
  private final IdempotencyRepository repo;
  private final BusinessService business;
  private final Clock clock;

  IdempotencyService(IdempotencyRepository repo, BusinessService business, Clock clock) {
    this.repo = Objects.requireNonNull(repo);
    this.business = Objects.requireNonNull(business);
    this.clock = Objects.requireNonNull(clock);
  }

  Response handleWithIdempotency(String idemKey, Request request, Duration dedupWindow) {
    Objects.requireNonNull(idemKey);
    Objects.requireNonNull(request);
    Objects.requireNonNull(dedupWindow);

    Instant now = clock.now();
    Instant expiresAt = now.plus(dedupWindow);

    // BEGIN TRANSACTION (simulated)
    IdempotencyInsertResult insert = repo.tryInsertNew(
        idemKey,
        request.fingerprint(),
        now,
        expiresAt,
        Status.IN_PROGRESS
    );

    if (insert.wasInserted()) {
      try {
        Response result = business.process(request);
        repo.markCompleted(idemKey, clock.now(), expiresAt, serializeResponse(result));
        return result;
      } catch (RuntimeException e) {
        repo.markFailed(idemKey, clock.now(), expiresAt, serializeError(e));
        throw e;
      }
    }

    IdempotencyRecord existing = repo.findByKeyForUpdate(idemKey);

    if (existing.expiresAt().isBefore(now)) {
      repo.deleteByKey(idemKey);
      // retry once after removing expired row
      return handleWithIdempotency(idemKey, request, dedupWindow);
    }

    if (!existing.requestFingerprint().equals(request.fingerprint())) {
      throw new IdempotencyConflictException("Same idempotency key used with different request payload");
    }

    if (existing.status() == Status.COMPLETED) {
      return deserializeResponse(existing.responseBody());
    }

    if (existing.status() == Status.FAILED) {
      throw deserializeError(existing.errorBody());
    }

    return Response.inProgress("Request is being processed; retry with same key");
    // END TRANSACTION
  }

  private String serializeResponse(Response r) {
    // Minimal, deterministic encoding for demo purposes.
    return r.status() + "|" + r.body();
  }

  private Response deserializeResponse(String body) {
    if (body == null) return new Response("UNKNOWN", "<missing cached response>");
    int idx = body.indexOf('|');
    if (idx < 0) return new Response("OK", body);
    return new Response(body.substring(0, idx), body.substring(idx + 1));
  }

  private String serializeError(RuntimeException e) {
    return e.getClass().getName() + "|" + (e.getMessage() == null ? "" : e.getMessage());
  }

  private RuntimeException deserializeError(String body) {
    if (body == null) return new RuntimeException("<missing cached error>");
    int idx = body.indexOf('|');
    String msg = idx < 0 ? body : body.substring(idx + 1);
    return new RuntimeException("Cached failure: " + msg);
  }
}

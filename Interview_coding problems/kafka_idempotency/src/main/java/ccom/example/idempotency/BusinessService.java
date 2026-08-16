package com.example.idempotency;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

class BusinessService {
  private final Clock clock;

  BusinessService(Clock clock) {
    this.clock = Objects.requireNonNull(clock);
  }

  Response process(Request req) {
    // Simulate side effects by generating a new id each time this is actually executed.
    String id = UUID.randomUUID().toString();
    Instant processedAt = clock.now();
    String body = "id=" + id + ", processedAt=" + processedAt + ", payload=" + req.payload();
    return new Response("OK", body);
  }
}

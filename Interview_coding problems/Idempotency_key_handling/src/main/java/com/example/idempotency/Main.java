package com.example.idempotency;

import java.time.Duration;
import java.time.Instant;

class Main {
  static void main(String[] args) {
    Clock clock = new Clock(Instant.parse("2026-01-01T00:00:00Z"));
    IdempotencyRepository repo = new InMemoryIdempotencyRepository();
    BusinessService business = new BusinessService(clock);
    IdempotencyService idem = new IdempotencyService(repo, business, clock);

    Duration dedupWindow = Duration.ofMinutes(10);

    String key = "idem-key-123";
    Request req = new Request("create-order: sku=ABC qty=1");

    Response r1 = idem.handleWithIdempotency(key, req, dedupWindow);
    print("Input", "key=" + key + ", payload=" + req.payload());
    print("Output (first)", r1.toString());

    Response r2 = idem.handleWithIdempotency(key, req, dedupWindow);
    print("Input", "key=" + key + ", payload=" + req.payload());
    print("Output (replay)", r2.toString());

    clock.advance(Duration.ofMinutes(30));
    int deleted = repo.deleteExpired(clock.now());
    print("Output (cleanup)", "deleted=" + deleted + ", now=" + clock.now());

    Response r3 = idem.handleWithIdempotency(key, req, dedupWindow);
    print("Input", "key=" + key + ", payload=" + req.payload());
    print("Output (post-expiry)", r3.toString());
  }

  private static void print(String label, String value) {
    System.out.println(label + ": " + value);
  }
}

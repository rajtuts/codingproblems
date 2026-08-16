package com.example.idempotency;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

class Clock {
  private Instant current;

  Clock(Instant start) {
    this.current = Objects.requireNonNull(start);
  }

  Instant now() {
    return current;
  }

  void advance(Duration d) {
    current = current.plus(Objects.requireNonNull(d));
  }
}

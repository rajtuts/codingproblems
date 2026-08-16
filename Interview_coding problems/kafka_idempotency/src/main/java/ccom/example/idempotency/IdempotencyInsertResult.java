package com.example.idempotency;

class IdempotencyInsertResult {
  private final boolean inserted;

  private IdempotencyInsertResult(boolean inserted) {
    this.inserted = inserted;
  }

  static IdempotencyInsertResult inserted() {
    return new IdempotencyInsertResult(true);
  }

  static IdempotencyInsertResult notInserted() {
    return new IdempotencyInsertResult(false);
  }

  boolean wasInserted() {
    return inserted;
  }
}

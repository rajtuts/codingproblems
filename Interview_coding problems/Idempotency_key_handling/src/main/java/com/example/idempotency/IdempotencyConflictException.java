package com.example.idempotency;

class IdempotencyConflictException extends RuntimeException {
  IdempotencyConflictException(String message) {
    super(message);
  }
}

package com.example.resilience.client;

class BadRequestException extends RuntimeException {
  BadRequestException(String message) {
    super(message);
  }
}

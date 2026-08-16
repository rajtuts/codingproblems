package com.example.idempotency;

import java.util.Objects;

class Response {
  private final String status;
  private final String body;

  Response(String status, String body) {
    this.status = Objects.requireNonNull(status);
    this.body = Objects.requireNonNull(body);
  }

  String status() {
    return status;
  }

  String body() {
    return body;
  }

  static Response inProgress(String message) {
    return new Response("IN_PROGRESS", Objects.requireNonNull(message));
  }

  @Override
  public String toString() {
    return "Response{status='" + status + "', body='" + body + "'}";
  }
}

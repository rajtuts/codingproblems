package com.example.idempotency;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Objects;

class Request {
  private final String payload;

  Request(String payload) {
    this.payload = Objects.requireNonNull(payload);
  }

  String payload() {
    return payload;
  }

  String fingerprint() {
    // In real systems, hash a canonical JSON representation of relevant fields.
    return sha256Hex(payload.trim());
  }

  private static String sha256Hex(String s) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] digest = md.digest(s.getBytes(StandardCharsets.UTF_8));
      return HexFormat.of().formatHex(digest);
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException("SHA-256 not available", e);
    }
  }
}

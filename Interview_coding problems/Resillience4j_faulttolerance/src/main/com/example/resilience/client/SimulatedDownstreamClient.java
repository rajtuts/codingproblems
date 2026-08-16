package com.example.resilience.client;

import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
class SimulatedDownstreamClient {

  String invoke(String mode) {
    return switch (mode == null ? "" : mode) {
      case "ok" -> "downstream-ok";
      case "slow" -> {
        sleepSilently(700);
        yield "downstream-slow";
      }
      case "timeout" -> {
        sleepSilently(2000);
        yield "downstream-timeout-late";
      }
      case "fail" -> throw ioUnchecked(new IOException("simulated I/O failure"));
      case "badrequest" -> throw new BadRequestException("simulated 400");
      default -> "downstream-default";
    };
  }

  private static void sleepSilently(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  private static RuntimeException ioUnchecked(IOException e) {
    return new RuntimeException(e);
  }
}

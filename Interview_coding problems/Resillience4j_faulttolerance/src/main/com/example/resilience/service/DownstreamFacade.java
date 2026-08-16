package com.example.resilience.service;

import com.example.resilience.client.SimulatedDownstreamClient;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import org.springframework.stereotype.Service;

@Service
class DownstreamFacade {
  private static final String INSTANCE = "downstreamService";

  private final SimulatedDownstreamClient client;
  private final Executor asyncExecutor = Executors.newCachedThreadPool();

  DownstreamFacade(SimulatedDownstreamClient client) {
    this.client = client;
  }

  @CircuitBreaker(name = INSTANCE, fallbackMethod = "syncFallback")
  @Bulkhead(name = INSTANCE)
  String callSync(String mode) {
    return client.invoke(mode);
  }

  @CircuitBreaker(name = INSTANCE, fallbackMethod = "asyncFallback")
  @Bulkhead(name = INSTANCE)
  @TimeLimiter(name = INSTANCE)
  CompletableFuture<String> callAsync(String mode) {
    return CompletableFuture.supplyAsync(() -> client.invoke(mode), asyncExecutor);
  }

  private String syncFallback(String mode, Throwable t) {
    Throwable root = unwrap(t);

    if (root instanceof CallNotPermittedException) {
      return "FALLBACK: circuit open";
    }
    if (root instanceof BulkheadFullException) {
      return "FALLBACK: bulkhead full";
    }
    if (root instanceof TimeoutException) {
      return "FALLBACK: timed out";
    }
    return "FALLBACK: generic error=" + root.getClass().getSimpleName();
  }

  private CompletableFuture<String> asyncFallback(String mode, Throwable t) {
    return CompletableFuture.completedFuture(syncFallback(mode, t));
  }

  private static Throwable unwrap(Throwable t) {
    if (t instanceof CompletionException && t.getCause() != null) {
      return t.getCause();
    }
    return t;
  }
}

package com.example.clientconfig.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ResilienceConfig {
  private static final Logger log = LoggerFactory.getLogger(ResilienceConfig.class);

  @Bean
  Retry downstreamRetry(RetryRegistry registry) {
    return registry.retry("downstreamClient");
  }

  @Bean
  CircuitBreaker downstreamCircuitBreaker(CircuitBreakerRegistry registry) {
    return registry.circuitBreaker("downstreamClient");
  }

  @Bean
  TimeLimiter downstreamTimeLimiter(TimeLimiterRegistry registry) {
    return registry.timeLimiter("downstreamClient");
  }

  @Bean
  ApplicationRunner validateBudgets(ClientProps props, TimeLimiter timeLimiter, Retry retry) {
    return args -> {
      int maxAttempts = retry.getRetryConfig().getMaxAttempts();
      Duration wait = retry.getRetryConfig().getWaitDuration();
      Duration perAttempt = props.getWebClient().getResponseTimeout();

      Duration worstCase = perAttempt.multipliedBy(maxAttempts)
          .plus(wait.multipliedBy(Math.max(0, maxAttempts - 1)));

      Duration tl = timeLimiter.getTimeLimiterConfig().getTimeoutDuration();

      if (tl.compareTo(perAttempt) < 0) {
        log.warn("TimeLimiter timeoutDuration ({}) is less than WebClient responseTimeout ({}).", tl, perAttempt);
      }

      log.info("Downstream worst-case (responseTimeout * attempts + waits) = {} (attempts={}, wait={})", worstCase, maxAttempts, wait);
    };
  }
}

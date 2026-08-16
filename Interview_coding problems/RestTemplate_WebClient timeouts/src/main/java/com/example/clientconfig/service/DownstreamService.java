package com.example.clientconfig.service;

import com.example.clientconfig.config.ClientProps;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.retry.Retry;
import java.time.Duration;
import java.util.function.Supplier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
class DownstreamService {
  private final RestTemplate restTemplate;
  private final WebClient webClient;
  private final Retry retry;
  private final CircuitBreaker circuitBreaker;
  private final ClientProps props;

  DownstreamService(RestTemplate restTemplate,
                    WebClient webClient,
                    Retry downstreamRetry,
                    CircuitBreaker downstreamCircuitBreaker,
                    ClientProps props) {
    this.restTemplate = restTemplate;
    this.webClient = webClient;
    this.retry = downstreamRetry;
    this.circuitBreaker = downstreamCircuitBreaker;
    this.props = props;
  }

  String callWithRestTemplate() {
    Supplier<String> supplier = () -> {
      String url = props.getBaseUrl() + "/api/resource";
      ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
      return response.getBody();
    };

    Supplier<String> decorated = Decorators.ofSupplier(supplier)
        .withCircuitBreaker(circuitBreaker)
        .withRetry(retry)
        .withFallback(t -> "fallback-resttemplate")
        .decorate();

    return decorated.get();
  }

  String callWithWebClientBlockingForDemo() {
    Supplier<String> supplier = () -> {
      Mono<String> mono = webClient.get()
          .uri("/api/resource")
          .retrieve()
          .bodyToMono(String.class)
          .timeout(Duration.ofMillis(1700));

      return mono.block(); // demo only
    };

    Supplier<String> decorated = Decorators.ofSupplier(supplier)
        .withCircuitBreaker(circuitBreaker)
        .withRetry(retry)
        .withFallback(t -> "fallback-webclient")
        .decorate();

    return decorated.get();
  }
}

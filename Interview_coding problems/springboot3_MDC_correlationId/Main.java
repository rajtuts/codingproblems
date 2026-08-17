/*
 * Main.java (snippet-style pseudocode bundle; intentionally NOT executable/compilable)
 *
 * Demonstrates end-to-end correlation/trace context propagation:
 *  1) Spring Cloud Gateway GlobalFilter adds/forwards X-Request-Id
 *  2) WebFlux WebFilter populates MDC with requestId/traceId and clears it
 *  3) Logback pattern prints MDC keys (traceId/requestId)
 *  4) Kafka producer/consumer propagate context via record headers
 *
 * Notes:
 *  - In real Spring Boot 3 apps, prefer Micrometer Tracing (OTel/Brave) for trace/span IDs.
 *  - For reactive MDC correctness across threads, consider Reactor Context + an MDC context-lifter.
 *  - Ensure all services agree on header names (or use W3C traceparent/baggage).
 */

// Imports (pseudocode):
// - reactor.core.publisher.Mono
// - org.slf4j.MDC
// - org.springframework.cloud.gateway.filter.GlobalFilter
// - org.springframework.cloud.gateway.filter.GatewayFilterChain
// - org.springframework.web.server.ServerWebExchange
// - org.springframework.web.server.WebFilter
// - org.springframework.web.server.WebFilterChain
// - org.springframework.http.server.reactive.ServerHttpRequest
// - org.springframework.kafka.core.KafkaTemplate
// - org.apache.kafka.clients.producer.ProducerRecord
// - org.springframework.kafka.annotation.KafkaListener
// - org.apache.kafka.common.header.Headers
// - java.nio.charset.StandardCharsets
// - java.util.UUID

class Main {
  // No runtime main needed; this file is a snippet container.
}

// ------------------------------
// 1) Gateway: add requestId (or forward if present)
// ------------------------------
// In Spring Cloud Gateway, implement a GlobalFilter that:
// - checks incoming header X-Request-Id
// - if missing, generates UUID
// - mutates request to include X-Request-Id
class RequestIdGatewayGlobalFilter /* implements GlobalFilter */ {
  static final String HEADER_REQUEST_ID = "X-Request-Id";

  /*
  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();

    String existing = request.getHeaders().getFirst(HEADER_REQUEST_ID);
    String requestId = (existing != null && !existing.isBlank())
        ? existing
        : UUID.randomUUID().toString();

    ServerHttpRequest mutatedRequest = request.mutate()
        .header(HEADER_REQUEST_ID, requestId)
        .build();

    ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
    return chain.filter(mutatedExchange);
  }
  */
}

// ------------------------------
// 2) Service: WebFlux WebFilter adds MDC entries
// ------------------------------
// WebFilter responsibilities:
// - read X-Request-Id
// - read traceId from tracing system (Micrometer Tracing / Brave / OTel) OR from headers
// - put into MDC for log correlation
// - ensure MDC cleared at end to avoid leakage across reactive threads
class MdcWebFilter /* implements WebFilter */ {
  static final String HEADER_REQUEST_ID = "X-Request-Id";

  // Pseudocode dependency: tracer that can provide current span/trace
  // e.g., io.micrometer.tracing.Tracer tracer;
  Object tracer;

  /*
  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();

    String requestId = request.getHeaders().getFirst(HEADER_REQUEST_ID);
    if (requestId == null || requestId.isBlank()) {
      requestId = UUID.randomUUID().toString();
    }

    // Obtain traceId (preferred) from tracer current span
    Span span = tracer.currentSpan();
    String traceId = (span != null) ? span.context().traceId() : "";

    MDC.put("requestId", requestId);
    if (!traceId.isBlank()) {
      MDC.put("traceId", traceId);
    }

    return chain.filter(exchange)
        .doFinally(signalType -> {
          MDC.remove("requestId");
          MDC.remove("traceId");
        });
  }
  */
}

// ------------------------------
// 3) Logback pattern includes traceId/requestId
// ------------------------------
// logback-spring.xml snippet:
//
// <configuration>
//   <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
//     <encoder>
//       <pattern>
//         %d{yyyy-MM-dd'T'HH:mm:ss.SSS} %-5level [%thread] %logger{36}
//         traceId=%X{traceId:-} requestId=%X{requestId:-} - %msg%n
//       </pattern>
//     </encoder>
//   </appender>
//
//   <root level="INFO">
//     <appender-ref ref="CONSOLE"/>
//   </root>
// </configuration>
class LogbackPatternSnippet {
  // No code; see comment above.
}

// ------------------------------
// 4) Kafka: propagate context via headers
// ------------------------------
// Producer side:
// - when publishing, add X-Request-Id and traceId to Kafka headers
// - values come from MDC (or tracer)
class KafkaProducerWithHeaders {
  static final String HEADER_REQUEST_ID = "X-Request-Id";
  static final String HEADER_TRACE_ID = "X-Trace-Id";

  // KafkaTemplate<String, String> kafkaTemplate;
  Object kafkaTemplate;

  // io.micrometer.tracing.Tracer tracer;
  Object tracer;

  /*
  public void publish(String topic, String key, String payload) {
    String requestId = MDC.get("requestId");

    Span span = tracer.currentSpan();
    String traceId = (span != null) ? span.context().traceId() : MDC.get("traceId");

    ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, payload);

    if (requestId != null && !requestId.isBlank()) {
      record.headers().add(HEADER_REQUEST_ID, requestId.getBytes(StandardCharsets.UTF_8));
    }
    if (traceId != null && !traceId.isBlank()) {
      record.headers().add(HEADER_TRACE_ID, traceId.getBytes(StandardCharsets.UTF_8));
    }

    kafkaTemplate.send(record);
  }
  */
}

// Consumer side:
// - read headers
// - put into MDC for logs during processing
// - clear MDC after
class KafkaConsumerWithMdc {
  static final String HEADER_REQUEST_ID = "X-Request-Id";
  static final String HEADER_TRACE_ID = "X-Trace-Id";

  /*
  @KafkaListener(topics = "your-topic", groupId = "your-group")
  public void onMessage(String payload, Headers headers) {
    String requestId = headerAsString(headers, HEADER_REQUEST_ID);
    String traceId = headerAsString(headers, HEADER_TRACE_ID);

    if (requestId != null && !requestId.isBlank()) {
      MDC.put("requestId", requestId);
    }
    if (traceId != null && !traceId.isBlank()) {
      MDC.put("traceId", traceId);
    }

    try {
      // process message
      // log statements now include requestId/traceId via logback pattern
    } finally {
      MDC.remove("requestId");
      MDC.remove("traceId");
    }
  }

  private static String headerAsString(Headers headers, String key) {
    Header header = headers.lastHeader(key);
    return (header != null) ? new String(header.value(), StandardCharsets.UTF_8) : "";
  }
  */
}

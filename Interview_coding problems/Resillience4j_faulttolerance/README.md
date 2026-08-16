# Resilience4j + Spring Boot 3 demo

This project demonstrates:
- CircuitBreaker thresholds and state transitions
- Bulkhead isolation (semaphore bulkhead)
- Optional `TimeLimiter` for async calls
- Fallback mapping based on exception type
- Micrometer metrics export via Spring Boot Actuator (Prometheus endpoint enabled)

## Run

```bash
mvn spring-boot:run
```

App runs on `http://localhost:8080`.

## Try it

### Sync calls

```bash
curl "http://localhost:8080/api/demo/sync?mode=ok"
curl "http://localhost:8080/api/demo/sync?mode=slow"
curl "http://localhost:8080/api/demo/sync?mode=badrequest"
```

Trigger failures (repeat to open the circuit breaker):

```bash
for i in {1..15}; do curl -s "http://localhost:8080/api/demo/sync?mode=fail"; echo; done
```

### Async calls (TimeLimiter)

```bash
curl "http://localhost:8080/api/demo/async?mode=ok"
curl "http://localhost:8080/api/demo/async?mode=timeout"
```

## Metrics / Actuator

- Health: `http://localhost:8080/actuator/health`
- Metrics list: `http://localhost:8080/actuator/metrics`
- Prometheus scrape: `http://localhost:8080/actuator/prometheus`

Example metric queries:

```bash
curl "http://localhost:8080/actuator/metrics/resilience4j.circuitbreaker.calls"
curl "http://localhost:8080/actuator/metrics/resilience4j.circuitbreaker.state"
```

## Notes

- `badrequest` mode throws `BadRequestException`, which is configured under `ignoreExceptions`, so it should not count as a circuit breaker failure.
- Tune thresholds in `src/main/resources/application.yml`.

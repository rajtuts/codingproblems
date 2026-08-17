class Main {
  public static void main(String[] args) {
    String yamlSnippet = """
# application.yaml (Spring Boot + HikariCP)

spring:
  datasource:
    url: jdbc:postgresql://<host>:5432/<db>
    username: <user>
    password: <password>
    hikari:
      # Size this per instance/pod. Ensure total across replicas stays within DB limits.
      maximum-pool-size: 10
      minimum-idle: 2

      # How long a thread waits for a connection from the pool before failing.
      connection-timeout: 2000   # ms (typical: 500–5000)

      # How long to wait for a connection validation to complete.
      validation-timeout: 1000   # ms

      # Idle connection management.
      idle-timeout: 600000       # ms (10m)
      max-lifetime: 1800000      # ms (30m) keep < DB/network idle timeouts

      # Detect potential connection leaks (only enable temporarily; adds overhead/noise).
      leak-detection-threshold: 20000  # ms (e.g., 20s)

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  metrics:
    tags:
      application: ${spring.application.name:my-service}
""";

    String notes = """
Operational notes (pool sizing + starvation diagnosis)

1) Pool sizing rule of thumb (avoid exceeding DB max_connections)
   - (maxPoolSize per instance) * (number of replicas) + (admin/maintenance headroom)
     <= DB max_connections
   - Example: DB max_connections=200, 10 replicas, headroom=20
     => per-replica maxPoolSize <= (200-20)/10 = 18

2) connectionTimeout
   - If threads frequently hit connectionTimeout, the app is waiting too long for a pool slot.
   - Keep it low enough to fail fast (often 0.5–5s) and surface backpressure.

3) leakDetectionThreshold
   - Use to catch code paths that borrow connections and don’t return them promptly.
   - Enable temporarily during investigation; set above normal query/transaction time.

4) Starvation symptoms
   - Request latency spikes under load, thread pools backing up.
   - Hikari logs like: "Connection is not available, request timed out".
   - Many threads waiting for connections while DB is already saturated or queries are slow.

5) Actuator metrics to watch (Micrometer)
   - hikaricp.connections.active   (in-use)
   - hikaricp.connections.idle     (available)
   - hikaricp.connections.pending  (threads waiting)
   - hikaricp.connections.timeout  (count of timeouts)
   - hikaricp.connections.acquire  (timer/latency to acquire)

   Interpretation:
   - pending > 0 and active ~= maximumPoolSize => pool is exhausted.
   - acquire time rising + timeouts increasing => contention/starvation.

6) Remediation checklist
   - If DB CPU/IO is saturated: reduce pool size (less concurrency), fix slow queries, add indexes.
   - If DB has headroom but pool is too small: increase maximumPoolSize carefully.
   - Ensure transactions are short; avoid holding connections across remote calls.
   - Add query/transaction timeouts; verify proper connection closing in all paths.
""";

    System.out.println("=== Spring Boot HikariCP tuning snippet (application.yaml) ===\n");
    System.out.println(yamlSnippet);
    System.out.println("---\n");
    System.out.println(notes);
  }
}

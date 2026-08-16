import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

class Main {
  public static void main(String[] args) throws Exception {
    // Minimal Micrometer-like metrics (self-contained; swap with Micrometer MeterRegistry later).
    MeterFacade metrics = new MeterFacade();

    FakeRedisClient redis = new FakeRedisClient(Clock.systemUTC());
    FakeDistributedLock lock = new FakeDistributedLock();

    CacheAsideService cache = new CacheAsideService(
        redis,
        lock,
        metrics,
        Clock.systemUTC(),
        Duration.ofSeconds(2),      // base TTL
        Duration.ofSeconds(2),      // jitter max
        Duration.ofSeconds(3),      // stale window beyond soft-expire
        Duration.ofMillis(40),      // lock wait
        Duration.ofSeconds(2)       // lock lease (ignored in fake)
    );

    BackendLoader backend = new BackendLoader(metrics, 0.18, 50, 150);

    // Warm-up
    String warm = cache.get("A", () -> backend.load("A"));
    System.out.println("Warm-up: key=A -> " + warm);

    // Concurrent simulation
    int threads = 24;
    int tasks = 240;
    ExecutorService pool = Executors.newFixedThreadPool(threads);
    List<Callable<String>> calls = new ArrayList<>();

    for (int i = 0; i < tasks; i++) {
      final String key = (i % 3 == 0) ? "B" : "A"; // skew to create contention on A
      calls.add(() -> cache.get(key, () -> backend.load(key)));
    }

    List<Future<String>> futures = pool.invokeAll(calls);
    pool.shutdown();
    pool.awaitTermination(10, TimeUnit.SECONDS);

    // Print a few sample outputs
    System.out.println("Samples:");
    for (int i = 0; i < Math.min(10, futures.size()); i++) {
      System.out.println("  " + i + ": " + futures.get(i).get());
    }

    // Metric snapshot
    System.out.println("\n=== Metrics ===");
    System.out.println(metrics.snapshot());

    // Show current cache entries (for demo visibility)
    System.out.println("=== Cache State (debug) ===");
    redis.debugDump().entrySet().stream()
        .sorted(Comparator.comparing(Map.Entry::getKey))
        .forEach(e -> System.out.println("  " + e.getKey() + " -> " + e.getValue().toDebugString(Clock.systemUTC().instant())));
  }

  // Simulated backend loader with latency + failure rate.
  static final class BackendLoader {
    private final MeterFacade metrics;
    private final double failureRate;
    private final int minDelayMs;
    private final int maxDelayMs;
    private final Random rnd = new Random();

    BackendLoader(MeterFacade metrics, double failureRate, int minDelayMs, int maxDelayMs) {
      this.metrics = Objects.requireNonNull(metrics);
      this.failureRate = failureRate;
      this.minDelayMs = minDelayMs;
      this.maxDelayMs = maxDelayMs;
    }

    String load(String key) {
      return metrics.time("load_timer", () -> {
        sleepRandom(minDelayMs, maxDelayMs);
        if (rnd.nextDouble() < failureRate) {
          metrics.inc("load_failure");
          throw new RuntimeException("backend failure for key=" + key);
        }
        metrics.inc("load_success");
        return "value:" + key + ":" + Instant.now().toEpochMilli();
      });
    }

    private static void sleepRandom(int minMs, int maxMs) {
      int d = ThreadLocalRandom.current().nextInt(minMs, maxMs + 1);
      try {
        Thread.sleep(d);
      } catch (InterruptedException ie) {
        Thread.currentThread().interrupt();
      }
    }
  }
}

final class CacheEntry {
  final String value;
  final Instant writtenAt;
  final Instant softExpireAt;
  final Instant hardExpireAt;

  CacheEntry(String value, Instant writtenAt, Instant softExpireAt, Instant hardExpireAt) {
    this.value = value;
    this.writtenAt = writtenAt;
    this.softExpireAt = softExpireAt;
    this.hardExpireAt = hardExpireAt;
  }

  boolean isHardExpired(Instant now) {
    return !now.isBefore(hardExpireAt);
  }

  boolean isFresh(Instant now) {
    return now.isBefore(softExpireAt);
  }

  boolean isStaleButServeable(Instant now, Duration staleWindow) {
    if (isHardExpired(now)) return false;
    return now.isBefore(softExpireAt.plus(staleWindow));
  }

  String toDebugString(Instant now) {
    return "{value='" + value + "', writtenAt=" + writtenAt +
        ", softExpireAt=" + softExpireAt + " (" + (isFresh(now) ? "fresh" : "soft-expired") + ")" +
        ", hardExpireAt=" + hardExpireAt + " (" + (isHardExpired(now) ? "hard-expired" : "alive") + ")" +
        "}";
  }
}

final class CacheAsideService {
  private final FakeRedisClient redis;
  private final FakeDistributedLock lock;
  private final MeterFacade metrics;
  private final Clock clock;

  private final Duration baseTtl;
  private final Duration jitterMax;
  private final Duration staleWindow;
  private final Duration lockWait;
  private final Duration lockLease;

  CacheAsideService(
      FakeRedisClient redis,
      FakeDistributedLock lock,
      MeterFacade metrics,
      Clock clock,
      Duration baseTtl,
      Duration jitterMax,
      Duration staleWindow,
      Duration lockWait,
      Duration lockLease
  ) {
    this.redis = Objects.requireNonNull(redis);
    this.lock = Objects.requireNonNull(lock);
    this.metrics = Objects.requireNonNull(metrics);
    this.clock = Objects.requireNonNull(clock);
    this.baseTtl = Objects.requireNonNull(baseTtl);
    this.jitterMax = Objects.requireNonNull(jitterMax);
    this.staleWindow = Objects.requireNonNull(staleWindow);
    this.lockWait = Objects.requireNonNull(lockWait);
    this.lockLease = Objects.requireNonNull(lockLease);
  }

  String get(String key, Supplier<String> loader) {
    return metrics.time("total_get_timer", () -> {
      Instant now = clock.instant();

      CacheEntry entry = redis.get(key);
      if (entry != null && entry.isFresh(now)) {
        metrics.inc("cache_hit");
        return entry.value;
      }

      if (entry == null) metrics.inc("cache_miss");
      else metrics.inc("cache_soft_expired");

      String lockKey = "lock:" + key;
      boolean acquired = lock.tryAcquire(lockKey, lockWait, lockLease);
      if (!acquired) {
        metrics.inc("lock_contended");

        if (entry != null && entry.isStaleButServeable(now, staleWindow)) {
          metrics.inc("cache_stale_served");
          return entry.value;
        }

        // Last resort: brief backoff + one re-read.
        sleepRandom(20, 40);
        CacheEntry reread = redis.get(key);
        Instant now2 = clock.instant();
        if (reread != null && !reread.isHardExpired(now2)) {
          metrics.inc("cache_hit_after_backoff");
          return reread.value;
        }

        // Demo behavior: proceed without lock to keep moving.
        metrics.inc("load_without_lock");
        return loadAndMaybeWrite(key, loader, Optional.ofNullable(entry));
      }

      metrics.inc("lock_acquired");
      try {
        // Double-check after acquiring lock.
        CacheEntry afterLock = redis.get(key);
        Instant now3 = clock.instant();
        if (afterLock != null && afterLock.isFresh(now3)) {
          metrics.inc("cache_hit");
          return afterLock.value;
        }

        return loadAndMaybeWrite(key, loader, Optional.ofNullable(afterLock != null ? afterLock : entry));
      } finally {
        lock.release(lockKey);
      }
    });
  }

  private String loadAndMaybeWrite(String key, Supplier<String> loader, Optional<CacheEntry> prior) {
    Instant now = clock.instant();

    final String newValue;
    try {
      newValue = loader.get();
    } catch (RuntimeException e) {
      // loader already increments load_failure in this demo; still handle stale fallback here.
      CacheEntry prev = prior.orElse(null);
      if (prev != null && prev.isStaleButServeable(now, staleWindow)) {
        metrics.inc("cache_stale_served");
        return prev.value;
      }
      throw e;
    }

    Duration ttl = baseTtl.plus(randomDuration(jitterMax));
    Duration softTtl = computeSoftTtl(ttl);

    Instant writtenAt = clock.instant();
    CacheEntry newEntry = new CacheEntry(
        newValue,
        writtenAt,
        writtenAt.plus(softTtl),
        writtenAt.plus(ttl)
    );

    redis.set(key, newEntry, ttl);
    metrics.inc("cache_write");
    metrics.observe("cache_ttl_seconds", ttl.toMillis() / 1000.0);

    return newValue;
  }

  private Duration computeSoftTtl(Duration ttl) {
    // Keep soft TTL < hard TTL; 80% is a common choice.
    long softMillis = Math.max(50, (long) (ttl.toMillis() * 0.8));
    softMillis = Math.min(softMillis, Math.max(1, ttl.toMillis() - 1));
    return Duration.ofMillis(softMillis);
  }

  private static Duration randomDuration(Duration max) {
    if (max.isZero() || max.isNegative()) return Duration.ZERO;
    long maxMs = Math.max(0, max.toMillis());
    long ms = ThreadLocalRandom.current().nextLong(maxMs + 1);
    return Duration.ofMillis(ms);
  }

  private static void sleepRandom(int minMs, int maxMs) {
    int d = ThreadLocalRandom.current().nextInt(minMs, maxMs + 1);
    try {
      Thread.sleep(d);
    } catch (InterruptedException ie) {
      Thread.currentThread().interrupt();
    }
  }
}

final class FakeRedisClient {
  private final ConcurrentHashMap<String, CacheEntry> map = new ConcurrentHashMap<>();
  private final Clock clock;

  FakeRedisClient(Clock clock) {
    this.clock = Objects.requireNonNull(clock);
  }

  CacheEntry get(String key) {
    CacheEntry entry = map.get(key);
    if (entry == null) return null;

    Instant now = clock.instant();
    if (entry.isHardExpired(now)) {
      map.remove(key, entry);
      return null;
    }
    return entry;
  }

  void set(String key, CacheEntry entry, Duration ttl) {
    // TTL is encoded into entry.hardExpireAt; real Redis would use SETEX/PEXPIRE.
    map.put(key, entry);
  }

  Map<String, CacheEntry> debugDump() {
    return Map.copyOf(map);
  }
}

final class FakeDistributedLock {
  private final ConcurrentHashMap<String, ReentrantLock> locks = new ConcurrentHashMap<>();

  boolean tryAcquire(String lockKey, Duration wait, Duration lease) {
    ReentrantLock l = locks.computeIfAbsent(lockKey, k -> new ReentrantLock());
    try {
      // lease ignored in fake; real impl would set auto-expire/watchdog.
      return l.tryLock(Math.max(0, wait.toMillis()), TimeUnit.MILLISECONDS);
    } catch (InterruptedException ie) {
      Thread.currentThread().interrupt();
      return false;
    }
  }

  void release(String lockKey) {
    ReentrantLock l = locks.get(lockKey);
    if (l == null) return;
    if (l.isHeldByCurrentThread()) l.unlock();
  }
}

final class MeterFacade {
  private final ConcurrentHashMap<String, Counter> counters = new ConcurrentHashMap<>();
  private final ConcurrentHashMap<String, Timer> timers = new ConcurrentHashMap<>();
  private final ConcurrentHashMap<String, DistributionSummary> summaries = new ConcurrentHashMap<>();

  void inc(String name) {
    counters.computeIfAbsent(name, k -> new Counter()).inc();
  }

  void add(String name, long delta) {
    counters.computeIfAbsent(name, k -> new Counter()).add(delta);
  }

  void observe(String name, double value) {
    summaries.computeIfAbsent(name, k -> new DistributionSummary()).record(value);
  }

  <T> T time(String timerName, Supplier<T> supplier) {
    Timer t = timers.computeIfAbsent(timerName, k -> new Timer());
    long start = System.nanoTime();
    try {
      return supplier.get();
    } finally {
      t.recordNanos(System.nanoTime() - start);
    }
  }

  String snapshot() {
    StringBuilder sb = new StringBuilder();

    sb.append("Counters:\n");
    counters.entrySet().stream()
        .sorted(Map.Entry.comparingByKey())
        .forEach(e -> sb.append("  ").append(e.getKey()).append(" = ").append(e.getValue().count()).append('\n'));

    sb.append("Timers:\n");
    timers.entrySet().stream()
        .sorted(Map.Entry.comparingByKey())
        .forEach(e -> {
          TimerStats s = e.getValue().stats();
          sb.append("  ").append(e.getKey())
              .append(" count=").append(s.count)
              .append(" totalMs=").append(String.format("%.2f", s.totalNanos / 1_000_000.0))
              .append(" avgMs=").append(String.format("%.2f", s.avgNanos / 1_000_000.0))
              .append(" maxMs=").append(String.format("%.2f", s.maxNanos / 1_000_000.0))
              .append('\n');
        });

    if (!summaries.isEmpty()) {
      sb.append("Summaries:\n");
      summaries.entrySet().stream()
          .sorted(Map.Entry.comparingByKey())
          .forEach(e -> {
            SummaryStats s = e.getValue().stats();
            sb.append("  ").append(e.getKey())
                .append(" count=").append(s.count)
                .append(" avg=").append(String.format("%.3f", s.avg))
                .append(" min=").append(String.format("%.3f", s.min))
                .append(" max=").append(String.format("%.3f", s.max))
                .append('\n');
          });
    }

    return sb.toString();
  }

  private static final class Counter {
    private final AtomicLong v = new AtomicLong();

    void inc() {
      v.incrementAndGet();
    }

    void add(long delta) {
      v.addAndGet(delta);
    }

    long count() {
      return v.get();
    }
  }

  private static final class Timer {
    private final AtomicLong count = new AtomicLong();
    private final AtomicLong totalNanos = new AtomicLong();
    private final AtomicLong maxNanos = new AtomicLong();

    void recordNanos(long nanos) {
      count.incrementAndGet();
      totalNanos.addAndGet(Math.max(0, nanos));
      maxNanos.accumulateAndGet(Math.max(0, nanos), Math::max);
    }

    TimerStats stats() {
      long c = count.get();
      long total = totalNanos.get();
      long max = maxNanos.get();
      double avg = (c == 0) ? 0.0 : (double) total / c;
      return new TimerStats(c, total, avg, max);
    }
  }

  private record TimerStats(long count, long totalNanos, double avgNanos, long maxNanos) {}

  private static final class DistributionSummary {
    private final AtomicLong count = new AtomicLong();
    private final AtomicLong scaledSum = new AtomicLong();
    private final AtomicLong scaledMin = new AtomicLong(Long.MAX_VALUE);
    private final AtomicLong scaledMax = new AtomicLong(Long.MIN_VALUE);

    void record(double value) {
      long scaled = (long) Math.round(value * 1_000_000.0);
      count.incrementAndGet();
      scaledSum.addAndGet(scaled);
      scaledMin.accumulateAndGet(scaled, Math::min);
      scaledMax.accumulateAndGet(scaled, Math::max);
    }

    SummaryStats stats() {
      long c = count.get();
      if (c == 0) return new SummaryStats(0, 0, 0, 0);
      double sum = scaledSum.get() / 1_000_000.0;
      double min = scaledMin.get() / 1_000_000.0;
      double max = scaledMax.get() / 1_000_000.0;
      return new SummaryStats(c, sum / c, min, max);
    }
  }

  private record SummaryStats(long count, double avg, double min, double max) {}
}

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

class Main {
  // --- Domain models ---
  record Item(String id, String name) {}

  // Cursor pagination models (GraphQL Connection spec style)
  record PageInfo(boolean hasNextPage, boolean hasPreviousPage, String startCursor, String endCursor) {}
  record Edge<T>(T node, String cursor) {}
  record Connection<T>(List<Edge<T>> edges, PageInfo pageInfo, int totalCount) {}

  // --- Cursor helpers ---
  static final class CursorCodec {
    private static final String PREFIX = "offset:";

    static String encodeOffset(int offset) {
      String raw = PREFIX + offset;
      return Base64.getEncoder().encodeToString(raw.getBytes(StandardCharsets.UTF_8));
    }

    static int decodeOffsetOrDefault(String afterCursor, int defaultOffset) {
      if (afterCursor == null || afterCursor.isBlank()) return defaultOffset;
      try {
        String decoded = new String(Base64.getDecoder().decode(afterCursor), StandardCharsets.UTF_8);
        if (!decoded.startsWith(PREFIX)) return defaultOffset;
        return Integer.parseInt(decoded.substring(PREFIX.length()));
      } catch (IllegalArgumentException | NumberFormatException e) {
        return defaultOffset;
      }
    }

    private CursorCodec() {}
  }

  // --- Batched REST client simulation ---
  static final class BatchedRestClient {
    private static final int TOTAL = 50;
    private final ExecutorService ioPool;

    BatchedRestClient(ExecutorService ioPool) {
      this.ioPool = Objects.requireNonNull(ioPool);
    }

    // Simulate downstream endpoint: POST /items:batchGet { ids: [...] }
    CompletableFuture<Map<String, Item>> batchGetItemsById(List<String> ids) {
      List<String> stableIds = List.copyOf(ids);
      System.out.println("REST batch call: batchGetItemsById ids=" + stableIds);
      return CompletableFuture.supplyAsync(() -> {
        sleep(80);
        Map<String, Item> out = new LinkedHashMap<>();
        for (String id : stableIds) {
          out.put(id, new Item(id, "Name for " + id));
        }
        return out;
      }, ioPool);
    }

    CompletableFuture<List<String>> listItemIds(int offset, int limit) {
      System.out.println("REST call: listItemIds offset=" + offset + " limit=" + limit);
      return CompletableFuture.supplyAsync(() -> {
        sleep(50);
        int start = Math.max(0, offset);
        int endExclusive = Math.min(TOTAL, start + Math.max(0, limit));
        if (start >= endExclusive) return List.of();

        List<String> ids = new ArrayList<>(endExclusive - start);
        for (int i = start + 1; i <= endExclusive; i++) {
          ids.add("item-" + i);
        }
        return ids;
      }, ioPool);
    }

    CompletableFuture<Integer> getTotalCount() {
      System.out.println("REST call: getTotalCount");
      return CompletableFuture.supplyAsync(() -> {
        sleep(20);
        return TOTAL;
      }, ioPool);
    }

    private static void sleep(long ms) {
      try {
        Thread.sleep(ms);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new CompletionException(e);
      }
    }
  }

  // --- Minimal DataLoader-like implementation ---
  interface BatchLoader<K, V> {
    CompletableFuture<List<V>> load(List<K> keys);
  }

  static final class DataLoader<K, V> {
    private final BatchLoader<K, V> batchLoader;
    private final List<K> queue = new ArrayList<>();
    private final Map<K, CompletableFuture<V>> futureByKey = new ConcurrentHashMap<>();

    DataLoader(BatchLoader<K, V> batchLoader) {
      this.batchLoader = Objects.requireNonNull(batchLoader);
    }

    CompletableFuture<V> load(K key) {
      return futureByKey.computeIfAbsent(key, k -> {
        synchronized (queue) {
          queue.add(k);
        }
        return new CompletableFuture<>();
      });
    }

    CompletableFuture<Void> dispatch() {
      final List<K> keys;
      synchronized (queue) {
        if (queue.isEmpty()) return CompletableFuture.completedFuture(null);
        keys = List.copyOf(queue);
        queue.clear();
      }

      return batchLoader.load(keys).handle((values, err) -> {
        if (err != null) {
          for (K k : keys) {
            CompletableFuture<V> f = futureByKey.get(k);
            if (f != null && !f.isDone()) f.completeExceptionally(err);
          }
          return null;
        }

        if (values == null || values.size() != keys.size()) {
          IllegalStateException ise = new IllegalStateException(
              "BatchLoader returned " + (values == null ? "null" : values.size()) + " values for " + keys.size() + " keys");
          for (K k : keys) {
            CompletableFuture<V> f = futureByKey.get(k);
            if (f != null && !f.isDone()) f.completeExceptionally(ise);
          }
          return null;
        }

        for (int i = 0; i < keys.size(); i++) {
          K k = keys.get(i);
          V v = values.get(i);
          CompletableFuture<V> f = futureByKey.get(k);
          if (f != null && !f.isDone()) f.complete(v);
        }
        return null;
      });
    }
  }

  static final class DataLoaderRegistry {
    private final Map<String, DataLoader<?, ?>> loaders = new HashMap<>();

    void register(String name, DataLoader<?, ?> loader) {
      loaders.put(Objects.requireNonNull(name), Objects.requireNonNull(loader));
    }

    @SuppressWarnings("unchecked")
    <K, V> DataLoader<K, V> get(String name) {
      DataLoader<?, ?> loader = loaders.get(name);
      if (loader == null) throw new IllegalArgumentException("No DataLoader registered with name: " + name);
      return (DataLoader<K, V>) loader;
    }
  }

  // --- "Config" that registers DataLoader (mirrors Spring GraphQL wiring conceptually) ---
  static final class GraphqlDataLoaderConfig {
    static final String ITEM_BY_ID_LOADER = "itemByIdLoader";

    DataLoaderRegistry buildRegistry(BatchedRestClient restClient) {
      DataLoaderRegistry registry = new DataLoaderRegistry();

      BatchLoader<String, Item> batchLoader = keys ->
          restClient.batchGetItemsById(keys)
              .thenApply(map -> keys.stream().map(map::get).collect(Collectors.toList()));

      registry.register(ITEM_BY_ID_LOADER, new DataLoader<>(batchLoader));
      return registry;
    }
  }

  // --- Resolver-like service demonstrating pagination + DataLoader usage ---
  static final class ItemQueryResolver {
    private final BatchedRestClient restClient;

    ItemQueryResolver(BatchedRestClient restClient) {
      this.restClient = Objects.requireNonNull(restClient);
    }

    CompletableFuture<Connection<Item>> itemsConnection(DataLoaderRegistry registry, Integer first, String after) {
      int limit = (first == null || first <= 0) ? 5 : first;
      int offset = CursorCodec.decodeOffsetOrDefault(after, 0);

      CompletableFuture<List<String>> idsFuture = restClient.listItemIds(offset, limit);
      CompletableFuture<Integer> totalFuture = restClient.getTotalCount();

      return idsFuture.thenCompose(ids -> {
        DataLoader<String, Item> loader = registry.get(GraphqlDataLoaderConfig.ITEM_BY_ID_LOADER);

        List<CompletableFuture<Item>> itemFutures = new ArrayList<>(ids.size());
        for (String id : ids) itemFutures.add(loader.load(id));

        return loader.dispatch().thenApply(ignored -> {
          List<Edge<Item>> edges = new ArrayList<>(ids.size());
          for (int i = 0; i < ids.size(); i++) {
            Item item = itemFutures.get(i).join();
            String cursor = CursorCodec.encodeOffset(offset + i + 1); // cursor points *after* this item
            edges.add(new Edge<>(item, cursor));
          }

          int totalCount = totalFuture.join();
          String startCursor = edges.isEmpty() ? null : edges.getFirst().cursor();
          String endCursor = edges.isEmpty() ? null : edges.getLast().cursor();

          boolean hasPreviousPage = offset > 0;
          boolean hasNextPage = (offset + limit) < totalCount;

          PageInfo pageInfo = new PageInfo(hasNextPage, hasPreviousPage, startCursor, endCursor);
          return new Connection<>(Collections.unmodifiableList(edges), pageInfo, totalCount);
        });
      });
    }
  }

  public static void main(String[] args) {
    ExecutorService ioPool = Executors.newFixedThreadPool(4);
    try {
      BatchedRestClient restClient = new BatchedRestClient(ioPool);
      DataLoaderRegistry registry = new GraphqlDataLoaderConfig().buildRegistry(restClient);
      ItemQueryResolver resolver = new ItemQueryResolver(restClient);

      System.out.println("\n=== Demo 1: first page (first=5, after=null) ===");
      Connection<Item> c1 = resolver.itemsConnection(registry, 5, null).join();
      printConnection(c1);

      System.out.println("\n=== Demo 2: next page (first=5, after=endCursor from page 1) ===");
      String afterCursor = c1.pageInfo().endCursor();
      Connection<Item> c2 = resolver.itemsConnection(registry, 5, afterCursor).join();
      printConnection(c2);

      System.out.println("\nNote: Each page should show exactly one listItemIds call and one batchGetItemsById call.");
    } finally {
      ioPool.shutdown();
      try {
        ioPool.awaitTermination(2, TimeUnit.SECONDS);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  private static void printConnection(Connection<Item> c) {
    System.out.println("Output: Connection(totalCount=" + c.totalCount() + ")");
    for (int i = 0; i < c.edges().size(); i++) {
      Edge<Item> e = c.edges().get(i);
      System.out.println("  edge[" + i + "]: cursor=" + e.cursor() + ", node={id=" + e.node().id() + ", name=" + e.node().name() + "}");
    }
    PageInfo p = c.pageInfo();
    System.out.println("  pageInfo: {hasNextPage=" + p.hasNextPage()
        + ", hasPreviousPage=" + p.hasPreviousPage()
        + ", startCursor=" + p.startCursor()
        + ", endCursor=" + p.endCursor() + "}");
  }
}

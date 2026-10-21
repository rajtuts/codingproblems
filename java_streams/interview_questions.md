# Java Streams: Intermediate Interview Questions & Answers

**Common setup used in the examples:**

```java
import java.util.*;
import java.util.function.Function;
import java.util.stream.*;

record Employee(int id, String name, String dept, double salary, int age, String city) {}

List<Employee> emps = List.of(
    new Employee(1, "Asha",  "IT",      90000, 30, "Hyderabad"),
    new Employee(2, "Ravi",  "IT",      75000, 26, "Pune"),
    new Employee(3, "Meena", "HR",      60000, 35, "Chennai"),
    new Employee(4, "Kiran", "Finance", 85000, 40, "Hyderabad"),
    new Employee(5, "Neha",  "HR",      65000, 28, "Pune"),
    new Employee(6, "Arjun", "IT",      90000, 33, "Hyderabad")
);
```

---

## Part 1: Conceptual Questions

**Q1. What is the difference between intermediate and terminal operations?**
Intermediate operations (`filter`, `map`, `flatMap`, `sorted`, `distinct`, `limit`, `skip`, `peek`) return a new stream and are **lazy**. Nothing runs until a terminal operation (`collect`, `forEach`, `reduce`, `count`, `findFirst`, `anyMatch`) is called. Terminal operations produce a result or side effect and close the stream.

**Q2. What is the difference between `map()` and `flatMap()`?**
`map` transforms each element 1-to-1. `flatMap` transforms each element into a stream and flattens all of those streams into one.

```java
List<List<Integer>> nested = List.of(List.of(1, 2), List.of(3, 4));
nested.stream().map(List::stream).toList();      // List<Stream<Integer>>
nested.stream().flatMap(List::stream).toList();  // [1, 2, 3, 4]
```

**Q3. Can a stream be reused?**
No. After a terminal operation it throws `IllegalStateException: stream has already been operated upon or closed`. Use a `Supplier<Stream<T>>` to create fresh streams.

**Q4. What is the difference between `findFirst()` and `findAny()`?**
`findFirst` always returns the first element in encounter order. `findAny` may return any element, which gives better performance in parallel streams.

**Q5. What is the difference between `peek()` and `map()`?**
`peek` is mainly for debugging. It takes a `Consumer`, doesn't change elements, and may be skipped by optimizations (for example, with `count()` on a sized source). `map` transforms elements.

**Q6. What does "lazy evaluation" and "short-circuiting" mean?**
Lazy means intermediate operations run only when a terminal operation is present. Short-circuiting means the pipeline stops early (`limit`, `findFirst`, `anyMatch`), so elements are processed one at a time through the whole pipeline (vertical processing), not stage by stage.

**Q7. `reduce()` vs `collect()`?**
`reduce` is for immutable reduction (sum, max). `collect` is for mutable reduction into containers (List, Map, StringBuilder).

**Q8. When should you NOT use parallel streams?**
Avoid them with small data, stateful or order-dependent operations, blocking I/O, shared mutable state, or when the common ForkJoinPool is already busy. Also avoid them with poorly splittable sources such as `LinkedList`.

---

## Part 2: Coding Questions

### Q9. Get names of employees with salary > 70,000, sorted alphabetically

```java
List<String> names = emps.stream()
    .filter(e -> e.salary() > 70000)
    .map(Employee::name)
    .sorted()
    .toList();   // Java 16+ (use collect(Collectors.toList()) on older)
```

### Q10. Group employees by department

```java
Map<String, List<Employee>> byDept =
    emps.stream().collect(Collectors.groupingBy(Employee::dept));
```

### Q11. Count employees per department

```java
Map<String, Long> countByDept = emps.stream()
    .collect(Collectors.groupingBy(Employee::dept, Collectors.counting()));
```

### Q12. Average salary per department

```java
Map<String, Double> avgSalary = emps.stream()
    .collect(Collectors.groupingBy(Employee::dept,
             Collectors.averagingDouble(Employee::salary)));
```

### Q13. Highest-paid employee in each department

```java
Map<String, Optional<Employee>> top = emps.stream()
    .collect(Collectors.groupingBy(Employee::dept,
             Collectors.maxBy(Comparator.comparingDouble(Employee::salary))));

// Cleaner: unwrap the Optional
Map<String, Employee> top2 = emps.stream()
    .collect(Collectors.toMap(Employee::dept, Function.identity(),
             BinaryOperator.maxBy(Comparator.comparingDouble(Employee::salary))));
```

### Q14. Find the second-highest salary

```java
Optional<Double> second = emps.stream()
    .map(Employee::salary)
    .distinct()
    .sorted(Comparator.reverseOrder())
    .skip(1)
    .findFirst();
```
`distinct()` matters here. Without it, duplicate top salaries (90000 twice) would give the wrong answer.

### Q15. Top 3 highest-paid employees

```java
List<Employee> top3 = emps.stream()
    .sorted(Comparator.comparingDouble(Employee::salary).reversed())
    .limit(3)
    .toList();
```

### Q16. Partition employees by age ≥ 30

```java
Map<Boolean, List<Employee>> parts = emps.stream()
    .collect(Collectors.partitioningBy(e -> e.age() >= 30));
```
`partitioningBy` always has both `true` and `false` keys, even if one is empty.

### Q17. Sort by department, then salary descending, then name

```java
List<Employee> sorted = emps.stream()
    .sorted(Comparator.comparing(Employee::dept)
            .thenComparing(Employee::salary, Comparator.reverseOrder())
            .thenComparing(Employee::name))
    .toList();
```

### Q18. Join all names into a comma-separated string

```java
String result = emps.stream()
    .map(Employee::name)
    .collect(Collectors.joining(", ", "[", "]"));
// [Asha, Ravi, Meena, Kiran, Neha, Arjun]
```

### Q19. Convert a list to a Map (id → name) and handle duplicate keys

```java
Map<Integer, String> idToName = emps.stream()
    .collect(Collectors.toMap(Employee::id, Employee::name));

// If keys can collide, supply a merge function or you get IllegalStateException
Map<String, Double> deptTotal = emps.stream()
    .collect(Collectors.toMap(Employee::dept, Employee::salary, Double::sum));

// Preserve insertion order
Map<Integer, String> ordered = emps.stream()
    .collect(Collectors.toMap(Employee::id, Employee::name,
             (a, b) -> a, LinkedHashMap::new));
```

### Q20. Find duplicate elements in a list

```java
List<Integer> nums = List.of(1, 2, 3, 2, 4, 5, 1, 6);

// Using a Set
Set<Integer> seen = new HashSet<>();
Set<Integer> dups = nums.stream()
    .filter(n -> !seen.add(n))
    .collect(Collectors.toSet());       // [1, 2]

// Using frequency
List<Integer> dups2 = nums.stream()
    .collect(Collectors.groupingBy(n -> n, Collectors.counting()))
    .entrySet().stream()
    .filter(e -> e.getValue() > 1)
    .map(Map.Entry::getKey)
    .toList();
```

### Q21. Character frequency in a string

```java
String s = "programming";
Map<Character, Long> freq = s.chars()
    .mapToObj(c -> (char) c)
    .collect(Collectors.groupingBy(Function.identity(),
             LinkedHashMap::new, Collectors.counting()));
```

### Q22. First non-repeated character

```java
Optional<Character> first = s.chars()
    .mapToObj(c -> (char) c)
    .collect(Collectors.groupingBy(Function.identity(),
             LinkedHashMap::new, Collectors.counting()))
    .entrySet().stream()
    .filter(e -> e.getValue() == 1)
    .map(Map.Entry::getKey)
    .findFirst();
```
`LinkedHashMap` preserves encounter order, which is essential here.

### Q23. Flatten a list of lists and get distinct sorted values

```java
List<List<Integer>> lists = List.of(List.of(3, 1), List.of(2, 3), List.of(5, 4));
List<Integer> flat = lists.stream()
    .flatMap(List::stream)
    .distinct()
    .sorted()
    .toList();   // [1, 2, 3, 4, 5]
```

### Q24. Sum, average, min, max in one pass

```java
DoubleSummaryStatistics stats = emps.stream()
    .collect(Collectors.summarizingDouble(Employee::salary));
stats.getSum(); stats.getAverage(); stats.getMin(); stats.getMax(); stats.getCount();
```

### Q25. Two results in one pass with `teeing` (Java 12+)

```java
var result = emps.stream().collect(Collectors.teeing(
    Collectors.summingDouble(Employee::salary),
    Collectors.counting(),
    (sum, count) -> sum / count));   // average
```

### Q26. Longest string in a list

```java
List<String> words = List.of("java", "stream", "lambda", "api");
Optional<String> longest = words.stream()
    .max(Comparator.comparingInt(String::length));
```

### Q27. Sort a Map by value (descending)

```java
Map<String, Integer> map = Map.of("a", 3, "b", 1, "c", 2);
LinkedHashMap<String, Integer> sorted = map.entrySet().stream()
    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
             (a, b) -> a, LinkedHashMap::new));
```

### Q28. Generate numbers: range, iterate, generate

```java
IntStream.rangeClosed(1, 5).boxed().toList();              // [1..5]
Stream.iterate(1, n -> n * 2).limit(6).toList();           // [1,2,4,8,16,32]
Stream.iterate(1, n -> n <= 100, n -> n * 3).toList();     // Java 9+: with predicate
Stream.generate(() -> "x").limit(3).toList();              // [x, x, x]
```

### Q29. Sum of squares of even numbers

```java
int sum = IntStream.rangeClosed(1, 10)
    .filter(n -> n % 2 == 0)
    .map(n -> n * n)
    .sum();   // 220
```

### Q30. Check for any/all/none matches

```java
boolean anyHigh = emps.stream().anyMatch(e -> e.salary() > 88000);
boolean allAdults = emps.stream().allMatch(e -> e.age() >= 18);
boolean noneInDelhi = emps.stream().noneMatch(e -> e.city().equals("Delhi"));
```

### Q31. Departments with more than 1 employee

```java
List<String> depts = emps.stream()
    .collect(Collectors.groupingBy(Employee::dept, Collectors.counting()))
    .entrySet().stream()
    .filter(e -> e.getValue() > 1)
    .map(Map.Entry::getKey)
    .toList();
```

### Q32. Names grouped by department (mapping downstream collector)

```java
Map<String, List<String>> namesByDept = emps.stream()
    .collect(Collectors.groupingBy(Employee::dept,
             Collectors.mapping(Employee::name, Collectors.toList())));
```

### Q33. Multi-level grouping (dept → city)

```java
Map<String, Map<String, List<String>>> grouped = emps.stream()
    .collect(Collectors.groupingBy(Employee::dept,
             Collectors.groupingBy(Employee::city,
             Collectors.mapping(Employee::name, Collectors.toList()))));
```

### Q34. Total salary using `reduce`

```java
double total = emps.stream()
    .map(Employee::salary)
    .reduce(0.0, Double::sum);

// Without identity, it returns Optional
Optional<Double> maxSal = emps.stream()
    .map(Employee::salary)
    .reduce(Double::max);
```

### Q35. Check if a string is a palindrome

```java
String str = "madam";
boolean isPal = IntStream.range(0, str.length() / 2)
    .allMatch(i -> str.charAt(i) == str.charAt(str.length() - 1 - i));
```

### Q36. Reverse each word in a sentence

```java
String sentence = "java streams are fun";
String reversed = Arrays.stream(sentence.split(" "))
    .map(w -> new StringBuilder(w).reverse().toString())
    .collect(Collectors.joining(" "));
// avaj smaerts era nuf
```

### Q37. Find the employee with the highest salary, and the youngest among ties

```java
Optional<Employee> emp = emps.stream()
    .max(Comparator.comparingDouble(Employee::salary)
         .thenComparing(Comparator.comparingInt(Employee::age).reversed()));
```

### Q38. Common elements of two lists

```java
List<Integer> a = List.of(1, 2, 3, 4), b = List.of(3, 4, 5);
List<Integer> common = a.stream().filter(b::contains).toList();   // O(n*m)

// Better for large lists
Set<Integer> setB = new HashSet<>(b);
List<Integer> common2 = a.stream().filter(setB::contains).toList();
```

### Q39. Convert an array to a stream and back

```java
int[] arr = {5, 3, 8, 1};
int[] sortedArr = Arrays.stream(arr).sorted().toArray();
Integer[] boxed = Arrays.stream(arr).boxed().toArray(Integer[]::new);
String[] upper = Stream.of("a", "b").map(String::toUpperCase).toArray(String[]::new);
```

### Q40. `takeWhile` and `dropWhile` (Java 9+)

```java
List<Integer> n = List.of(1, 2, 3, 10, 4, 5);
n.stream().takeWhile(x -> x < 5).toList();   // [1, 2, 3]
n.stream().dropWhile(x -> x < 5).toList();   // [10, 4, 5]
```

---

## Part 3: Tricky "Output?" Questions

**Q41. What does this print?**
```java
Stream.of("a", "b", "c")
      .peek(s -> System.out.println("peek " + s))
      .map(String::toUpperCase);
```
**Nothing.** There is no terminal operation, so the pipeline never runs.

**Q42. In what order are elements processed?**
```java
Stream.of("a", "b")
      .filter(s -> { System.out.println("filter " + s); return true; })
      .map(s -> { System.out.println("map " + s); return s; })
      .toList();
```
Output: `filter a, map a, filter b, map b`. Each element goes through the whole pipeline before the next starts. With `sorted()` in the middle, it becomes a barrier that must see all elements first.

**Q43. What happens here?**
```java
Stream<Integer> s = Stream.of(1, 2, 3);
s.forEach(System.out::println);
s.forEach(System.out::println);
```
The second call throws `IllegalStateException`.

**Q44. Will this work?**
```java
List<Integer> list = new ArrayList<>(List.of(1, 2, 3));
list.stream().forEach(n -> list.add(n));
```
It throws `ConcurrentModificationException`. Do not modify the source during stream processing.

**Q45. What's wrong with this?**
```java
List<Integer> out = new ArrayList<>();
nums.parallelStream().forEach(out::add);
```
It is not thread-safe, because `ArrayList` is mutated from multiple threads, which causes lost updates or exceptions. Use `collect(Collectors.toList())` instead.

---

## Quick Cheat Sheet

| Need | Use |
|---|---|
| Transform 1-to-1 | `map` |
| Transform and flatten | `flatMap` |
| Remove duplicates | `distinct` |
| Pagination | `skip(n).limit(m)` |
| Group | `groupingBy` |
| Split true/false | `partitioningBy` |
| To Map | `toMap` (with merge function) |
| Primitive streams | `mapToInt`, `IntStream`, `boxed()` |
| Stats | `summarizingInt/Double` |
| Combine two collectors | `teeing` |

# Java Interview Q&A: Collectors Internals, Custom Collectors & Optional

**Common setup:**
```java
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import static java.util.stream.Collector.Characteristics.*;

record Employee(int id, String name, String dept, double salary) {}

List<Employee> emps = List.of(
    new Employee(1, "Asha",  "IT", 90000),
    new Employee(2, "Ravi",  "IT", 75000),
    new Employee(3, "Meena", "HR", 60000),
    new Employee(4, "Neha",  "HR", 65000)
);
```

---

# Part 1: Collectors Internals

**Q1. What are the components of a `Collector<T, A, R>`?**

| Component | Method | Purpose |
|---|---|---|
| Supplier | `supplier()` | Creates a new mutable container `A` |
| Accumulator | `accumulator()` | Adds one element `T` into the container |
| Combiner | `combiner()` | Merges two containers (used in parallel streams) |
| Finisher | `finisher()` | Converts the container `A` into the final result `R` |
| Characteristics | `characteristics()` | Hints that allow optimizations |

```java
// Roughly what collect() does sequentially:
A container = collector.supplier().get();
for (T t : stream) collector.accumulator().accept(container, t);
R result = collector.finisher().apply(container);
```

**Q2. What are the Collector characteristics?**

- `IDENTITY_FINISH`: the finisher is an identity function, so it can be skipped (the container `A` is the result `R`).
- `UNORDERED`: the result doesn't depend on encounter order.
- `CONCURRENT`: multiple threads may call the accumulator on the same container, so the container must be thread-safe (for example `ConcurrentHashMap`).

**Q3. How does `Collectors.toList()` work internally?**

```java
// Simplified equivalent
Collector<T, ?, List<T>> toList = Collector.of(
    ArrayList::new,                  // supplier
    List::add,                       // accumulator
    (left, right) -> { left.addAll(right); return left; },  // combiner
    IDENTITY_FINISH);
```

**Q4. How does `Collectors.joining()` work internally?**
It uses a `StringBuilder` (or `StringJoiner`) as the container, appends each element, and the finisher calls `toString()`. This is why it is not an identity finish.

```java
Collector<CharSequence, StringBuilder, String> joiner = Collector.of(
    StringBuilder::new,
    (sb, s) -> sb.append(s).append(","),
    StringBuilder::append,
    StringBuilder::toString);
```

**Q5. How does `groupingBy` work internally?**
It uses a `HashMap<K, A>`, where `A` is the downstream collector's container.

```java
// Simplified
Supplier<Map<K, A>> supplier = HashMap::new;

BiConsumer<Map<K, A>, T> accumulator = (map, t) -> {
    K key = classifier.apply(t);
    A container = map.computeIfAbsent(key, k -> downstream.supplier().get());
    downstream.accumulator().accept(container, t);
};

BinaryOperator<Map<K, A>> combiner = (m1, m2) -> {
    m2.forEach((k, v) -> m1.merge(k, v, downstream.combiner()));
    return m1;
};

// Finisher: apply downstream finisher to every value
Function<Map<K, A>, Map<K, D>> finisher = m -> {
    m.replaceAll((k, v) -> downstream.finisher().apply(v));
    return (Map<K, D>) m;
};
```
A `null` classifier result throws `NullPointerException: element cannot be mapped to a null key`.

**Q6. Why does `toMap` throw `IllegalStateException`, and when does it throw `NullPointerException`?**

```java
List<String> words = List.of("a", "b", "a");

words.stream().collect(Collectors.toMap(w -> w, String::length));
// IllegalStateException: Duplicate key a (attempted merging values 1 and 1)

// Fix: merge function
words.stream().collect(Collectors.toMap(w -> w, String::length, Integer::sum));
```
`toMap` also throws `NullPointerException` if the value mapper returns `null`. `groupingBy` doesn't have this problem, because downstream collectors handle values.

**Q7. How does a parallel `collect` work?**
The source is split, and each thread gets its **own container** from `supplier()`. Each thread accumulates its chunk, and then `combiner()` merges the partial containers. This is why the combiner must be **associative** and why you should not share external mutable state.

**Q8. Why use `collect()` instead of `reduce()` for mutable containers?**

```java
// WRONG: reduce with a shared mutable container, broken in parallel
List<Integer> bad = Stream.of(1, 2, 3).parallel()
    .reduce(new ArrayList<>(),
            (list, n) -> { list.add(n); return list; },
            (a, b) -> { a.addAll(b); return a; });  // same list shared across threads

// RIGHT: collect creates a separate container per thread
List<Integer> good = Stream.of(1, 2, 3).parallel().collect(Collectors.toList());
```

**Q9. `groupingBy` vs `groupingByConcurrent`?**
`groupingBy` builds separate maps per thread and merges them, which is safe but costs merge time. `groupingByConcurrent` uses one `ConcurrentHashMap` with the `CONCURRENT` characteristic, so it's faster for parallel streams, but the order of elements within each group is not guaranteed.

**Q10. `stream.toList()` vs `Collectors.toList()` vs `Collectors.toUnmodifiableList()`?**

```java
List<String> a = Stream.of("x", null).toList();                        // OK, unmodifiable, allows null
List<String> b = Stream.of("x", null).collect(Collectors.toList());    // OK, ArrayList (mutable)
Stream.of("x", null).collect(Collectors.toUnmodifiableList());         // NullPointerException
```

| Method | Mutable? | Nulls? |
|---|---|---|
| `Collectors.toList()` | Yes (`ArrayList`, not guaranteed) | Yes |
| `Stream.toList()` | No | Yes |
| `Collectors.toUnmodifiableList()` | No | No |

**Q11. `partitioningBy` vs `groupingBy`?**
`partitioningBy` takes a `Predicate`, always returns a `Map` with both `true` and `false` keys (even if empty), and uses a specialized two-key map. `groupingBy` takes a `Function` and only creates keys that appear.

**Q12. Why does `Collectors.maxBy` return `Optional`?**
The stream may be empty, and there is no sensible default result. To unwrap inside grouping, use `collectingAndThen`:

```java
Map<String, Employee> top = emps.stream()
    .collect(Collectors.groupingBy(Employee::dept,
        Collectors.collectingAndThen(
            Collectors.maxBy(Comparator.comparingDouble(Employee::salary)),
            Optional::get)));   // safe: groups are never empty
```

**Q13. How does `teeing` work internally?**
It runs two downstream collectors over the same stream in a single pass. It holds a pair of containers, feeds every element to both accumulators, and the finisher applies both finishers and passes the two results to the merger function.

```java
var result = emps.stream().collect(Collectors.teeing(
    Collectors.minBy(Comparator.comparingDouble(Employee::salary)),
    Collectors.maxBy(Comparator.comparingDouble(Employee::salary)),
    (min, max) -> min.get().name() + " - " + max.get().name()));
// Meena - Asha
```

---

# Part 2: Custom Collectors

### Q14. Write a custom collector using `Collector.of()` to join with a pipe

```java
Collector<String, StringBuilder, String> pipeJoiner = Collector.of(
    StringBuilder::new,
    (sb, s) -> { if (sb.length() > 0) sb.append("|"); sb.append(s); },
    (a, b) -> { if (a.length() > 0 && b.length() > 0) a.append("|"); return a.append(b); },
    StringBuilder::toString);

System.out.println(Stream.of("a", "b", "c").collect(pipeJoiner));   // a|b|c
```

### Q15. Implement the `Collector` interface fully (immutable list collector)

```java
class ImmutableListCollector<T> implements Collector<T, List<T>, List<T>> {
    @Override public Supplier<List<T>> supplier() { return ArrayList::new; }
    @Override public BiConsumer<List<T>, T> accumulator() { return List::add; }
    @Override public BinaryOperator<List<T>> combiner() {
        return (a, b) -> { a.addAll(b); return a; };
    }
    @Override public Function<List<T>, List<T>> finisher() { return List::copyOf; }
    @Override public Set<Characteristics> characteristics() { return Set.of(); }
    // No IDENTITY_FINISH because the finisher does real work
}

List<Integer> list = Stream.of(1, 2, 3).collect(new ImmutableListCollector<>());
list.add(4);   // UnsupportedOperationException
```

### Q16. Custom collector: average using a custom accumulator class

```java
class AvgAcc {
    double sum; long count;
    void add(double d) { sum += d; count++; }
    AvgAcc merge(AvgAcc o) { sum += o.sum; count += o.count; return this; }
    double result() { return count == 0 ? 0 : sum / count; }
}

Collector<Double, AvgAcc, Double> avg =
    Collector.of(AvgAcc::new, AvgAcc::add, AvgAcc::merge, AvgAcc::result);

double result = emps.stream().map(Employee::salary).collect(avg);
System.out.println(result);   // 72500.0
```
A mutable accumulator class is needed because a `double` primitive can't be mutated inside a lambda.

### Q17. Custom collector: top N elements

```java
static <T> Collector<T, PriorityQueue<T>, List<T>> topN(int n, Comparator<T> cmp) {
    return Collector.<T, PriorityQueue<T>, List<T>>of(
        () -> new PriorityQueue<>(cmp),                       // min-heap
        (pq, t) -> { pq.offer(t); if (pq.size() > n) pq.poll(); },
        (a, b) -> { b.forEach(x -> { a.offer(x); if (a.size() > n) a.poll(); }); return a; },
        pq -> { List<T> l = new ArrayList<>(pq); l.sort(cmp.reversed()); return l; });
}

System.out.println(Stream.of(5, 1, 9, 3, 7).collect(topN(3, Comparator.naturalOrder())));
// [9, 7, 5]
```
This is O(n log k) in time and O(k) in memory, better than sorting the whole stream.

### Q18. Custom collector: split a stream into batches of size N

```java
static <T> Collector<T, List<List<T>>, List<List<T>>> batching(int size) {
    return Collector.<T, List<List<T>>>of(
        ArrayList::new,
        (batches, item) -> {
            if (batches.isEmpty() || batches.get(batches.size() - 1).size() == size)
                batches.add(new ArrayList<>());
            batches.get(batches.size() - 1).add(item);
        },
        (a, b) -> { throw new UnsupportedOperationException("sequential only"); });
}

System.out.println(IntStream.rangeClosed(1, 7).boxed().collect(batching(3)));
// [[1, 2, 3], [4, 5, 6], [7]]
```
Batching is order-dependent, so a correct combiner isn't practical. State this limitation in the interview and use it only on sequential streams.

### Q19. Custom collector: count and sum in one result object

```java
record Stats(long count, double total) {}

Collector<Employee, double[], Stats> statsCollector = Collector.of(
    () -> new double[2],
    (a, e) -> { a[0]++; a[1] += e.salary(); },
    (a, b) -> { a[0] += b[0]; a[1] += b[1]; return a; },
    a -> new Stats((long) a[0], a[1]));

System.out.println(emps.stream().collect(statsCollector));
// Stats[count=4, total=290000.0]
```

### Q20. Custom downstream collector used inside `groupingBy`

```java
Map<String, String> namesByDept = emps.stream()
    .collect(Collectors.groupingBy(Employee::dept,
        Collector.of(
            StringBuilder::new,
            (sb, e) -> sb.append(sb.length() > 0 ? "/" : "").append(e.name()),
            StringBuilder::append,
            StringBuilder::toString)));
// {HR=Meena/Neha, IT=Asha/Ravi}
```

### Q21. What makes a custom collector correct? (Checklist)

1. The supplier must return a **new** container every time.
2. The accumulator must only mutate the container passed in, with no external state.
3. The combiner must be **associative** and either return one of its arguments or a new container.
4. Declare `IDENTITY_FINISH` only if the finisher is truly identity.
5. Declare `CONCURRENT` only if the container is thread-safe.
6. Declare `UNORDERED` only if the result does not depend on encounter order.

### Q22. What happens if you wrongly declare `IDENTITY_FINISH`?

```java
// BUG: declares IDENTITY_FINISH but the container type differs from the result type
Collector<String, StringBuilder, String> bad = Collector.of(
    StringBuilder::new, StringBuilder::append, StringBuilder::append,
    StringBuilder::toString, IDENTITY_FINISH);
// ClassCastException at runtime: StringBuilder cannot be cast to String
```

---

# Part 3: Optional

**Q23. Why was `Optional` introduced?**
To represent "a value may be absent" explicitly in return types, reduce `NullPointerException`s, and enable fluent, null-safe chains. It is not meant to replace every null.

**Q24. Ways to create an Optional**

```java
Optional<String> a = Optional.of("hello");         // value must be non-null
Optional<String> b = Optional.ofNullable(null);    // empty if null
Optional<String> c = Optional.empty();

Optional.of(null);   // NullPointerException
```

**Q25. `orElse()` vs `orElseGet()`: what is the difference?**
`orElse` evaluates its argument **eagerly**, even when a value is present. `orElseGet` evaluates the supplier **lazily**, only when empty.

```java
static String expensive() { System.out.println("called!"); return "default"; }

Optional.of("value").orElse(expensive());          // prints "called!" (wasted work)
Optional.of("value").orElseGet(() -> expensive()); // prints nothing
```
Use `orElseGet` when the fallback is costly or has side effects (DB calls, object creation).

**Q26. `orElseThrow()` forms**

```java
Employee e = emps.stream()
    .filter(x -> x.id() == 99)
    .findFirst()
    .orElseThrow(() -> new NoSuchElementException("Employee 99 not found"));

// Java 10+: no-arg, same as get() but with a clearer name
String s = Optional.of("x").orElseThrow();
```

**Q27. `map()` vs `flatMap()` on Optional**

```java
record User(String name, Optional<String> email) {}

Optional<User> user = Optional.of(new User("Asha", Optional.of("asha@x.com")));

Optional<Optional<String>> nested = user.map(User::email);       // nested Optional
Optional<String> flat = user.flatMap(User::email);               // flattened
```
Use `flatMap` when the mapping function itself returns an `Optional`.

**Q28. Null-safe chain: refactor nested null checks**

```java
record Address(String city) {}
record Person(String name, Address address) {}

// Before
String city = null;
if (person != null && person.address() != null) city = person.address().city();

// After
String city2 = Optional.ofNullable(person)
    .map(Person::address)
    .map(Address::city)
    .orElse("Unknown");
```
If a mapper returns `null`, `map` produces an empty Optional rather than throwing.

**Q29. `filter()` on Optional**

```java
Optional<Employee> rich = emps.stream().findFirst()
    .filter(e -> e.salary() > 100000);
System.out.println(rich.isPresent());   // false
```

**Q30. `ifPresent` and `ifPresentOrElse` (Java 9+)**

```java
Optional<String> name = Optional.of("Asha");
name.ifPresent(n -> System.out.println("Hi " + n));

Optional.empty().ifPresentOrElse(
    v -> System.out.println("Found " + v),
    () -> System.out.println("Nothing found"));   // Nothing found
```

**Q31. `or()` (Java 9+): fallback to another Optional**

```java
Optional<String> result = Optional.<String>empty()
    .or(() -> Optional.of("backup"));
System.out.println(result.get());   // backup
```
Unlike `orElseGet`, which returns a value `T`, `or` returns an `Optional<T>`, so you can chain multiple fallbacks.

**Q32. `Optional.stream()` (Java 9+): filter out empties from a list of Optionals**

```java
List<Optional<String>> opts = List.of(Optional.of("a"), Optional.empty(), Optional.of("b"));

List<String> values = opts.stream()
    .flatMap(Optional::stream)
    .toList();
System.out.println(values);   // [a, b]
```
This replaces the older `.filter(Optional::isPresent).map(Optional::get)`.

**Q33. Find an employee by ID and return their name, or "N/A"**

```java
String name = emps.stream()
    .filter(e -> e.id() == 3)
    .findFirst()
    .map(Employee::name)
    .orElse("N/A");
System.out.println(name);   // Meena
```

**Q34. Primitive Optionals**

```java
OptionalInt max = IntStream.of(3, 9, 2).max();
System.out.println(max.getAsInt());   // 9

OptionalDouble avg = emps.stream().mapToDouble(Employee::salary).average();
System.out.println(avg.orElse(0));    // 72500.0
```
`OptionalInt`, `OptionalLong` and `OptionalDouble` avoid boxing but lack `map`, `filter` and `flatMap`.

**Q35. `Optional.ofNullable` with a Map lookup**

```java
Map<Integer, String> map = Map.of(1, "one");
String v = Optional.ofNullable(map.get(2)).orElse("missing");
// Simpler alternative: map.getOrDefault(2, "missing")
```

**Q36. Optional anti-patterns (frequently asked)**

```java
// 1. isPresent() + get(): defeats the purpose
if (opt.isPresent()) { use(opt.get()); }        // BAD
opt.ifPresent(v -> use(v));                      // GOOD

// 2. Optional as a field or method parameter
class Bad { private Optional<String> name; }    // BAD: not Serializable, adds overhead
void process(Optional<String> x) {}             // BAD: use overloads or nullable param

// 3. Optional wrapping collections
Optional<List<String>> bad;                      // BAD
List<String> good = Collections.emptyList();     // GOOD: return an empty collection

// 4. Calling get() without checking
opt.get();                                       // NoSuchElementException if empty

// 5. Optional.of() on a possibly-null value
Optional.of(maybeNull);                          // BAD: use ofNullable
```

**Q37. Is `Optional` Serializable? Why does that matter?**
No. That is one reason it shouldn't be used as a field in entities or DTOs. It was designed mainly as a **return type**.

**Q38. How do `equals`, `hashCode` and `toString` behave on Optional?**

```java
Optional.of("a").equals(Optional.of("a"));   // true
Optional.empty().equals(Optional.empty());   // true
Optional.of("a").toString();                 // Optional[a]
Optional.empty().toString();                 // Optional.empty
```

**Q39. Convert an Optional to a Stream and combine it with `flatMap`**

```java
// Names of employees whose IDs exist in a lookup
List<Integer> ids = List.of(1, 3, 99);
List<String> names = ids.stream()
    .map(id -> emps.stream().filter(e -> e.id() == id).findFirst())
    .flatMap(Optional::stream)
    .map(Employee::name)
    .toList();
System.out.println(names);   // [Asha, Meena]
```

---

# Part 4: Tricky "Output?" Questions

**Q40. What is the output?**
```java
Optional<String> o = Optional.of("a").map(s -> null);
System.out.println(o.isPresent());
```
**`false`.** `map` wraps a `null` result into `Optional.empty()`.

**Q41. What is the output?**
```java
Optional.empty().get();
```
**`NoSuchElementException: No value present`**

**Q42. What is the output?**
```java
Optional<String> o = Optional.of("x");
System.out.println(o.orElse(print("A")));
System.out.println(o.orElseGet(() -> print("B")));

static String print(String s) { System.out.println("called " + s); return s; }
```
Output:
```
called A
x
x
```
`orElse` always evaluates its argument. `orElseGet` doesn't, because the value is present.

**Q43. Will this compile and run?**
```java
Map<String, Integer> m = Stream.of("a", "bb", "a")
    .collect(Collectors.toMap(s -> s, String::length));
```
It compiles but throws `IllegalStateException: Duplicate key a`. Add a merge function.

**Q44. What is the output?**
```java
Map<Boolean, List<Integer>> m = Stream.of(2, 4)
    .collect(Collectors.partitioningBy(n -> n > 10));
System.out.println(m);
```
**`{false=[2, 4], true=[]}`.** `partitioningBy` always includes both keys.

**Q45. What is wrong with this custom collector?**
```java
Collector.of(
    () -> new ArrayList<Integer>(),
    List::add,
    (a, b) -> a,           // BUG
    IDENTITY_FINISH);
```
The combiner discards `b`, so elements are **lost in parallel streams**. It should be `(a, b) -> { a.addAll(b); return a; }`. It works sequentially, so the bug hides until someone calls `.parallel()`.

**Q46. What does this return?**
```java
Optional<Integer> r = Stream.<Integer>empty().reduce(Integer::sum);
Integer r2 = Stream.<Integer>empty().reduce(0, Integer::sum);
```
`r` is `Optional.empty`, while `r2` is `0`. Without an identity value, an empty stream has no result, so `reduce` returns an `Optional`.

---

# Quick Cheat Sheet

| Topic | Key point |
|---|---|
| Collector parts | supplier, accumulator, combiner, finisher, characteristics |
| Parallel collect | Per-thread container, then combiner merges, so the combiner must be associative |
| `IDENTITY_FINISH` | Only if the container is the result |
| `CONCURRENT` | Only if the container is thread-safe |
| `toMap` | Duplicate keys throw `IllegalStateException`, null values throw `NullPointerException` |
| `groupingBy` | `computeIfAbsent` + downstream; null key throws `NullPointerException` |
| `orElse` vs `orElseGet` | Eager vs lazy |
| `map` vs `flatMap` | Use `flatMap` when the function returns an `Optional` |
| `Optional.of(null)` | `NullPointerException`, use `ofNullable` |
| Optional usage | Return types only; not for fields, parameters or collections |
| `Optional.stream()` | Best way to drop empties in a stream pipeline |
